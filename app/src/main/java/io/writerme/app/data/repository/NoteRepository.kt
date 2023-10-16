package io.writerme.app.data.repository

import android.util.Log
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.isManaged
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.Sort
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.utils.deleteHistory
import io.writerme.app.utils.deleteNote
import io.writerme.app.utils.getDefaultInstance
import io.writerme.app.utils.getLast
import kotlinx.coroutines.flow.Flow
import java.io.Closeable

class NoteRepository: Repository(), Closeable {
    private val realm: Realm = Realm.getDefaultInstance()

    suspend fun createNewNote() : Flow<ObjectChange<Note>> {
        return realm.write {
            val note = copyToRealm(Note(), UpdatePolicy.ALL)

            val titleHistory = copyToRealm(History().apply { this.id = nextId()})
            val titleComponent = Component(note, "").apply { this.id = nextId() }
            titleHistory.push(copyToRealm(titleComponent))

            val coverHistory = copyToRealm(History().apply { this.id = nextId() })

            val textHistory = copyToRealm(History().apply { this.id = nextId() })
            val textComponent = Component(note, "").apply { this.id = nextId() }
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

                val note = this.query(Note::class, "id == $0", saved.noteId).first().find()
                note?.changeTime = System.currentTimeMillis()

                saved
            }
        }
    }

    suspend fun updateNoteCoverImage(noteId: Long, uri: String?) {
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
                if (it.cover == null) {
                    it.cover = copyToRealm(
                        History().apply { this.id = nextId() },
                        UpdatePolicy.ALL
                    )
                }

                it.cover!!.push(image)
                it.changeTime = System.currentTimeMillis()
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
                it.changeTime = System.currentTimeMillis()
            }
        }
    }

    suspend fun deleteTag(noteId: Long, tag: String) {
        realm.write {
            val note = this.query(Note::class, "id = $0", noteId).first().find()
            note?.tags?.remove(tag)
            note?.changeTime = System.currentTimeMillis()
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
                it.changeTime = System.currentTimeMillis()
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

                deleteTextIfNecessary(this, note)

                val history = this.copyToRealm(History(), UpdatePolicy.ALL)
                history.changes.add(checkBox)

                note?.let {
                    if (currentPosition < 0 || (currentPosition + 1 == it.content.size)) {
                        it.content.add(history)
                    } else {
                        it.content.add(currentPosition + 1, history)
                    }
                }
                note?.changeTime = System.currentTimeMillis()
            }

            addTextIfNecessary(noteId)
        }
    }

    suspend fun addSection(noteId: Long, comp: Component) {
        if (noteId >= 0) {
            realm.write {
                val note = this.query(Note::class, "id == $0", noteId).first().find()

                deleteTextIfNecessary(this, note)

                val component = if (comp.isManaged()) findLatest(comp)!! else copyToRealm(comp, UpdatePolicy.ALL)
                val history = copyToRealm(History(), UpdatePolicy.ALL)
                history.push(component)

                note?.content?.add(history)
                note?.changeTime = System.currentTimeMillis()
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
                    it.changeTime = System.currentTimeMillis()
                }
            }
        }
    }

    suspend fun deleteNote(noteId: Long) {
        realm.write {
            val note = this.query(Note::class, "id == $0", noteId).first().find()
            note?.let {
                deleteNote(it)
            }
        }
    }

    override fun close() {
        realm.close()
    }

    suspend fun toggleCheckbox(component: Component) {
        realm.write {
            findLatest(component)?.let { checkbox ->
                checkbox.isChecked = !checkbox.isChecked

                val note = this.query(Note::class, "id == $0", checkbox.noteId).first().find()
                note?.changeTime = System.currentTimeMillis()
            }
        }
    }

    private fun deleteTextIfNecessary(realm: MutableRealm, note: Note?) {
        val lastHistory = note?.content?.getLast()
        lastHistory?.let { history ->
            history.newest()?.let { last ->
                if (last.type == ComponentType.Text && last.content.isEmpty()) {
                    realm.deleteHistory(history)
                }
            }
        }
    }
}