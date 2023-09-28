package io.writerme.app.data.repository

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.Sort
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.utils.getDefaultInstance
import io.writerme.app.utils.getLast
import kotlinx.coroutines.flow.Flow
import java.io.Closeable

class NoteRepository: Repository(), Closeable {
    private val realm: Realm = Realm.getDefaultInstance()

    suspend fun createNewNote() : Flow<ObjectChange<Note>> {
        return realm.write {
            val note = copyToRealm(Note(), UpdatePolicy.ALL)
            val id = System.currentTimeMillis()

            val titleHistory = copyToRealm(History().apply { this.id = id + 1})
            val titleComponent = Component(note, "").apply { this.id = id + 2 }
            titleHistory.push(copyToRealm(titleComponent))

            val coverHistory = copyToRealm(History().apply { this.id = id + 3 })

            val textHistory = copyToRealm(History().apply { this.id = id + 4 })
            val textComponent = Component(note, "").apply { this.id = id + 5 }
            textHistory.push(copyToRealm(textComponent))

            note.title = titleHistory
            note.cover = coverHistory
            note.content.add(textHistory)

            note
        }.asFlow()
    }

    fun getNotes(): Flow<ResultsChange<Note>> {
        return realm.query<Note>().sort("changeTime", Sort.DESCENDING).find().asFlow()
    }

    suspend fun getNote(noteId: Long) : Flow<ObjectChange<Note>> {
        return realm.query(Note::class, "id == $0", noteId).first().find()?.asFlow() ?: createNewNote()
    }

    suspend fun saveComponent(component: Component): Component {
        return realm.write {
            this.copyToRealm(component, UpdatePolicy.ALL)
        }
    }

    suspend fun updateHistory(historyId: Long, component: Component) {
        realm.write {
            val history = this.query(History::class, "id == $0", historyId).first().find()

            history?.let {
                val saved = this.copyToRealm(component, UpdatePolicy.ALL)

                val toDelete = it.push(saved)
                toDelete?.let { obj -> delete(obj) }

                copyToRealm(it)
                saved
            }
        }
    }

    suspend fun updateNoteCoverImage(noteId: Long, uri: String) {
        realm.write {
            Log.d("NoteRepository", "updateNoteCoverImage")
            val note = this.query(Note::class, "id == $0", noteId).first().find()

            val component = Component().apply {
                this.noteId = noteId
                this.mediaUrl = uri
                this.type = ComponentType.Image
            }
            val image = copyToRealm(component, UpdatePolicy.ALL)

            note?.let {
                if (note.cover == null) {
                    note.cover = copyToRealm(
                        History().apply { this.id = component.id+1 },
                        UpdatePolicy.ALL
                    )
                }

                note.cover!!.push(image)
            }
        }
    }

    suspend fun addNewTag(noteId: Long, tag: String) {
        realm.write {
            val note = this.query(Note::class, "id == $0", noteId).first().find()

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
            val note = this.query(Note::class, "id == $0", noteId).first().find()

            note?.let {
                if (it.content.isNotEmpty()) {
                    val lastHistory = it.content.getLast()!!

                    val type = lastHistory.getType()

                    if (type == null || type != ComponentType.Text) {
                        val textComponent = Component().apply {
                            this.type = ComponentType.Text
                            this.noteId = noteId
                        }
                        val text = copyToRealm(textComponent, UpdatePolicy.ALL)

                        if (type == null) {
                            lastHistory.push(text)
                        } else {
                            var history = History()
                            history = copyToRealm(history, UpdatePolicy.ALL)
                            history.push(textComponent)

                            note.content.add(history)
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

                val checkBox = copyToRealm(component, UpdatePolicy.ALL)

                val note = this.query(Note::class, "id == $0", noteId).first().find()

                val history = this.copyToRealm(History(), UpdatePolicy.ALL)
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
                val component = copyToRealm(comp, UpdatePolicy.ALL)
                val history = copyToRealm(History(), UpdatePolicy.ALL)
                history.push(component)

                val note = this.query(Note::class, "id == $0", noteId).first().find()
                note?.content?.add(history)
            }

            addTextIfNecessary(noteId)
        }
    }

    suspend fun toggleImportance(noteId: Long) {
        if (noteId >= 0) {
            realm.write {
                val note = this.query(Note::class, "id == $0", noteId).first().find()
                note?.let {
                    it.isImportant = !it.isImportant
                }
            }
        }
    }

    suspend fun deleteNote(noteId: Long) {
        realm.write {
            val note = this.query(Note::class, "id == $0", noteId).first().find()
            note?.let { delete(it) }
        }
    }

    override fun close() {
        realm.close()
    }
}