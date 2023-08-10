package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.notifications.ObjectChange
import io.writerme.app.data.model.Settings
import io.writerme.app.utils.getDefaultInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.Closeable

class SettingsRepository(private val scope: CoroutineScope) : Closeable {
    private lateinit var realm: Realm

    init {
        scope.launch(Dispatchers.Main) {
            realm = Realm.getDefaultInstance()
        }
    }

    suspend fun getSettings(): Flow<ObjectChange<Settings>> {
        val result = realm.query(Settings::class, "id = $0", 0).first().find()

        val settings: Settings = result
            ?: realm.write {
                val s = Settings()
                copyToRealm(s)
            }

        return settings.asFlow()
    }

    override fun close() {
        realm.close()
    }
}