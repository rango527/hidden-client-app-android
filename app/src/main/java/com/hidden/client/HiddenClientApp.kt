package com.hidden.client

import android.app.Application
import com.hidden.client.helpers.AppPreferences

class HiddenClientApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppPreferences.init(this)
    }
}