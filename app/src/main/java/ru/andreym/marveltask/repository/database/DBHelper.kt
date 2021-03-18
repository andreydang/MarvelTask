package ru.andreym.marveltask.repository.database

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class DBHelper(context: Context)  {
    init {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
                .name("mount.realm")
                .schemaVersion(1)
                .build()
        Realm.setDefaultConfiguration(config)
    }

    fun getDBInstance(): Realm = Realm.getDefaultInstance()
}