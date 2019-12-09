package com.hidden.client

import android.app.Application
import com.hidden.client.helpers.AppPreferences

class HiddenClientApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppPreferences.init(this)

//        FontsOverride.setDefaultFcont(this, "DEFAULT", "fonts/alex.ttf");
//        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/alex.ttf");
//        FontsOverride.setDefaultFont(this, "SERIF", "fonts/alex.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/alex.ttf");

//        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/alex.ttf");
    }
}