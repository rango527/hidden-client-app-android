package com.hidden.client.helpers

import android.app.Activity
import android.util.Log
import com.hidden.client.models_.HCLogin
import com.hidden.client.ui.viewmodels___.HCJobDetailTileViewModel

class HCGlobal{

    init { }

    companion object {

        private var INSTANCE: HCGlobal = HCGlobal()

        fun getInstance() : HCGlobal{
            return INSTANCE
        }
    }

    fun log(message: String) {
        if (APP.is_debug) {
            Log.d(HCConstants.LOG_TAG, message)
        }
    }


    // Global variable
    lateinit var currentActivity: Activity           // current activity instance
    var myInfo: HCLogin = HCLogin()                 // user info after login

    lateinit var currrentJobTitleList: ArrayList<HCJobDetailTileViewModel>
}