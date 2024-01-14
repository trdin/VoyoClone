package com.example.voyoclone

import android.app.Application
import timber.log.Timber

class App : Application() {

    private var DEBUG = true
    override fun onCreate() {
        super.onCreate()


        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}