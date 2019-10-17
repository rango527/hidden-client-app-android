package com.hidden.client.helpers

import android.app.Activity
import android.content.Context
import android.util.Log
import com.hidden.client.models.HCClient

class HCGlobal{

    init { }

    companion object {

        private var INSTANCE: HCGlobal = HCGlobal()

        fun getInstance() : HCGlobal{
            return INSTANCE
        }
    }

    fun log(message: String) {
        if (HCConstants.IS_DEBUG) {
            Log.d(HCConstants.LOG_TAG, message)
        }
    }


    // Global variable
    lateinit var currentActivity: Activity;           // current activity instance
    var myInfo: HCClient = HCClient()                 // user info after login

}