package io.writerme.app

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class WriterMe: Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val configuration = RealmConfiguration.Builder()
            .name("writer.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)    // TODO: remove
            .allowQueriesOnUiThread(true)   // TODO: remove
            .build()

        Realm.setDefaultConfiguration(configuration)
    }
}