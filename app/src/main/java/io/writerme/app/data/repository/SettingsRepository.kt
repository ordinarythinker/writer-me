package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.notifications.ObjectChange
import io.writerme.app.data.model.Settings
import io.writerme.app.utils.Const
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

    private suspend fun _getSettings(): Settings {
        val result = realm.query(Settings::class, "id = $0", 0).first().find()

        return result
            ?: realm.write {
                val s = Settings()
                copyToRealm(s)
            }
    }

    suspend fun getSettings(): Flow<ObjectChange<Settings>> {
        return _getSettings().asFlow()
    }

    suspend fun setCounter(key: String, value: Int) {
        val settings = _getSettings()

        realm.write {
            when(key) {
                Const.MEDIA_CHANGES_HISTORY_KEY -> settings.mediaChanges = value
                Const.VOICE_CHANGES_HISTORY_KEY -> settings.voiceChanges = value
                Const.TEXT_CHANGES_HISTORY_KEY -> settings.textChanges = value
                Const.TASK_CHANGES_HISTORY_KEY -> settings.taskChanges = value
                Const.LINK_CHANGES_HISTORY_KEY -> settings.linkChanges = value
            }
        }
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        val settings = _getSettings()

        realm.write {
            settings.isDarkMode = isDarkMode
        }
    }

    suspend fun setLanguage(language: String) {
        val settings = _getSettings()

        realm.write {
            settings.currentLanguage = language
        }
    }

    override fun close() {
        realm.close()
    }
}