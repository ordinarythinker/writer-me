package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.writerme.app.data.model.Component
import io.writerme.app.utils.getDefaultInstance
import java.io.Closeable

class NoteRepository: Closeable {
    private val realm: Realm = Realm.getDefaultInstance()

    suspend fun dealHistory(execute: () -> Component?) {
//        if (scope.isActive) {
//            scope.launch {
//                realm.write {
//                    val c = execute()
//
//                    c?.let { component ->
//                        this.delete(component)
//                    }
//                }
//            }
//        }
    }


    override fun close() {
        realm.close()
    }
}