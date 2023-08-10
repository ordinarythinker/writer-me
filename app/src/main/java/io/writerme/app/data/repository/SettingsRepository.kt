package io.writerme.app.data.repository

import io.reactivex.Flowable
import io.realm.Realm
import io.writerme.app.data.model.Settings
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

    fun getSettings(): Flowable<Settings?> {
        return realm
            .where(Settings::class.java)
            .equalTo("id", 0L)
            .findFirst()
            ?.asFlowable<Settings>() ?: Flowable.empty()
    }

    override fun close() {
        realm.close()
    }
}