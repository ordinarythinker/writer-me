package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.writerme.app.utils.getDefaultInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Closeable

class BookmarksRepository(scope: CoroutineScope): Closeable {

    lateinit var realm: Realm

    init {
        scope.launch(Dispatchers.Main) {
            realm = Realm.getDefaultInstance()
        }
    }

    override fun close() {
        realm.close()
    }
}