package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.utils.getDefaultInstance
import io.writerme.app.utils.getLast
import java.io.Closeable

class NoteRepository: Closeable {
    private val realm: Realm = Realm.getDefaultInstance()

    suspend fun saveComponent(component: Component) {
        realm.write {
            this.copyToRealm(component, UpdatePolicy.ALL)
        }
    }

    suspend fun updateHistory(historyId: Long, component: Component) {
        realm.write {
            val history = this.query(History::class, "id = $0", historyId).first().find()

            history?.let {
                val saved = this.copyToRealm(component, UpdatePolicy.ALL)

                val toDelete = it.push(saved)
                toDelete?.let { obj -> delete(obj) }
                saved
            }
        }
    }

    private suspend fun addTextIfNecessary(noteId: Long) {
        realm.write {
            val note = this.query(Note::class, "id = $0", noteId).first().find()

            note?.let {
                if (it.content.isNotEmpty()) {
                    val lastHistory = it.content.getLast()!!

                    val type = lastHistory.getType()

                    if (type == null || type != ComponentType.Text) {
                        val textComponent = Component().apply {
                            this.type = ComponentType.Text
                            this.noteId = noteId
                        }
                        val text = copyToRealm(textComponent)

                        if (type == null) {
                            lastHistory.push(text)
                        } else if (type != ComponentType.Text) {
                            var history = History()
                            history = copyToRealm(history)
                            history.push(textComponent)
                        }
                    }
                }
            }
        }
    }

    suspend fun addNewCheckBox(noteId: Long, currentPosition: Int = -1) {
        if (noteId >= 0) {

            realm.write {
                val component = Component().apply {
                    this.noteId = noteId
                    this.type = ComponentType.Checkbox
                }

                val checkBox = copyToRealm(component)

                val note = this.query(Note::class, "id = $0", noteId).first().find()

                val history = this.copyToRealm(History())
                history.changes.add(checkBox)

                note?.let {
                    if (currentPosition < 0 || (currentPosition + 1 == it.content.size)) {
                        it.content.add(history)
                    } else {
                        it.content.add(currentPosition + 1, history)
                    }
                }
            }

            addTextIfNecessary(noteId)
        }
    }

    override fun close() {
        realm.close()
    }
}