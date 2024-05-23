package com.hidden.client

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.setThreadPolicy
import android.os.StrictMode.setVmPolicy
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.urbanairship.UAirship

class HiddenClientApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppPreferences.init(this)

        val pushId = """client-${AppPreferences.myId}"""
        if (UAirship.shared().namedUser.id != pushId) {
            UAirship.shared().namedUser.id = """client-${AppPreferences.myId}"""
        }

        if (BuildConfig.DEBUG) {
            setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }
}