package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.writerme.app.data.model.Component
import io.writerme.app.utils.getDefaultInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.Closeable

class NoteRepository(private val scope: CoroutineScope): Closeable {
    private lateinit var realm: Realm

    init {
        scope.launch(Dispatchers.Main) {
            realm = Realm.getDefaultInstance()
        }
    }

    fun dealHistory(execute: () -> Component?) {
        if (scope.isActive) {
            scope.launch {
//                realm.write {
//                    val c = execute()
//
//                    c?.let { component ->
//                        this.delete(component)
//                    }
//                }
            }
        }
    }


    override fun close() {
        realm.close()
    }
}