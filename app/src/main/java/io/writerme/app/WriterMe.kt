package io.writerme.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WriterMe: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}