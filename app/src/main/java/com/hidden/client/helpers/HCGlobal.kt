package com.hidden.client.helpers

import android.app.Activity
import android.content.Context
import android.util.Log
import com.hidden.client.models.HCClient

class HCGlobal private constructor(context: Context) {

    init { }
    companion object : HCSingletonHolder<HCGlobal, Context>(::HCGlobal)

    fun HCLog(message: String) {
        if (IS_DEBUG)
            Log.d(this.LOG_TAG, message)
    }

    // Global Constants
    private val IS_DEBUG = true
    private val LOG_TAG: String = "HiddenClient"

    val IMAGE_TYPE_CIRCLE: Int = 0
    val IMAGE_TYPE_ROUNDED_RECTANGLE: Int = 1


    // Global variable
    lateinit var g_currentActivity: Activity;           // current activity instance
    var g_client: HCClient = HCClient()                 // user info after login

}