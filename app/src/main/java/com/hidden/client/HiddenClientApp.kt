package com.hidden.client

import android.app.Application
import android.os.StrictMode
import com.hidden.client.helpers.AppPreferences
import android.os.StrictMode.VmPolicy
import android.os.StrictMode.setVmPolicy
import android.os.StrictMode.setThreadPolicy



class HiddenClientApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppPreferences.init(this)

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }
}