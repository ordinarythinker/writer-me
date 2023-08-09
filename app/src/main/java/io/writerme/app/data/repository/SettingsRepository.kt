package io.writerme.app.data.repository

import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Closeable

class SettingsRepository(private val scope: CoroutineScope) : Closeable {
    private lateinit var realm: Realm

    init {
        scope.launch(Dispatchers.Main) {
            realm = Realm.getDefaultInstance()
        }
    }


    override fun close() {
        realm.close()
    }
}