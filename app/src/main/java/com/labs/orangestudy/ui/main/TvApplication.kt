package com.labs.orangestudy.ui.main

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class TvApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("Tv.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(configuration)
    }
}