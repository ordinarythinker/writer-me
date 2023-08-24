package io.writerme.app.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.notifications.ObjectChange
import io.writerme.app.data.model.Settings
import io.writerme.app.utils.Const
import io.writerme.app.utils.getDefaultInstance
import kotlinx.coroutines.flow.Flow
import java.io.Closeable

class SettingsRepository : Closeable {
    private val realm: Realm = Realm.getDefaultInstance()

    private suspend fun _getSettings(): Settings {
        val result = realm.query(Settings::class, "id = $0", 0).first().find()

        return result
            ?: realm.write {
                val s = Settings()
                // TODO: remove in production
                s.apply {
                    fullName = "Florian Hermes"
                    email = "florian.hermes@email.com"
                }
                copyToRealm(s)
            }
    }

    suspend fun getSettings(): Flow<ObjectChange<Settings>> {
        return _getSettings().asFlow()
    }

    suspend fun setCounter(key: String, value: Int) {

        realm.write {
            val settings = this.query(Settings::class, "id = $0", 0).first().find()

            when(key) {
                Const.MEDIA_CHANGES_HISTORY_KEY -> settings?.mediaChanges = value
                Const.VOICE_CHANGES_HISTORY_KEY -> settings?.voiceChanges = value
                Const.TEXT_CHANGES_HISTORY_KEY -> settings?.textChanges = value
                Const.TASK_CHANGES_HISTORY_KEY -> settings?.taskChanges = value
                Const.LINK_CHANGES_HISTORY_KEY -> settings?.linkChanges = value
            }
        }
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        realm.write {
            val settings = this.query(Settings::class, "id = $0", 0).first().find()

            settings?.isDarkMode = isDarkMode
        }
    }

    suspend fun setLanguage(language: String) {
        realm.write {
            val settings = this.query(Settings::class, "id = $0", 0).first().find()

            settings?.currentLanguage = language
        }
    }

    override fun close() {
        realm.close()
    }
}