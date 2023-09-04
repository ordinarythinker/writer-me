package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.History
import io.writerme.app.utils.getDefaultInstance
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

    override fun close() {
        realm.close()
    }
}