package com.hidden.client

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.setThreadPolicy
import android.os.StrictMode.setVmPolicy
import com.hidden.client.helpers.AppPreferences


class HiddenClientApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppPreferences.init(this)

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