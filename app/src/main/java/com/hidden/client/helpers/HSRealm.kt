package com.hidden.client.helpers

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class HSRealm {

    var mRealm: Realm? = null
    var mAutoNum = 1

    fun init(context: Context) {
        Realm.init(context)
    }

    fun config(email: String, server: String) {

        val user = email.replace("@", "")
        val prefix = server.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        val fName = "$user-$prefix.realm"

        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().name(fName).build()
        Realm.setDefaultConfiguration(config)
        mRealm = Realm.getDefaultInstance()
    }

    fun close() {
        mRealm?.close()
    }

    
}