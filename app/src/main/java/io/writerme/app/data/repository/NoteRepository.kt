package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.notifications.ObjectChange
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.utils.getDefaultInstance
import io.writerme.app.utils.getLast
import kotlinx.coroutines.flow.Flow
import java.io.Closeable

class NoteRepository: Closeable {
    private val realm: Realm = Realm.getDefaultInstance()

    suspend fun createNewNote() : Flow<ObjectChange<Note>> {
        return realm.write {
            copyToRealm(Note())
        }.asFlow()
    }

    suspend fun getNote(noteId: Long) : Flow<ObjectChange<Note>> {
        return realm.query(Note::class, "id = $0", noteId).first().find()?.asFlow() ?: createNewNote()
    }

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

    suspend fun updateNoteTitle(noteId: Long, title: Component) {
        realm.write {
            val note = this.query(Note::class, "id = $0", noteId).first().find()

            note?.let {

                if (note.title == null) {
                    note.title = copyToRealm(History())
                }

                note.title!!.push(copyToRealm(title, UpdatePolicy.ALL))
            }
        }
    }

    suspend fun updateNoteCover(noteId: Long, cover: Component) {
        realm.write {
            val note = this.query(Note::class, "id = $0", noteId).first().find()

            note?.let {
                if (note.cover == null) {
                    note.cover = copyToRealm(History())
                }

                note.cover!!.push(copyToRealm(cover, UpdatePolicy.ALL))
            }
        }
    }

    suspend fun addNewTag(noteId: Long, tag: String) {
        realm.write {
            val note = this.query(Note::class, "id = $0", noteId).first().find()

            note?.let {
                if (!note.tags.contains(tag)) {
                    note.tags.add(tag)
                }
            }
        }
    }

    suspend fun deleteTag(noteId: Long, tag: String) {
        realm.write {
            val note = this.query(Note::class, "id = $0", noteId).first().find()

            note?.tags?.remove(tag)
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

    suspend fun addSection(noteId: Long, comp: Component) {
        if (noteId >= 0) {
            realm.write {
                val component = copyToRealm(comp)
                val history = copyToRealm(History())
                history.push(component)

                val note = this.query(Note::class, "id = $0", noteId).first().find()
                note?.content?.add(history)
            }

            addTextIfNecessary(noteId)
        }
    }

    override fun close() {
        realm.close()
    }
}