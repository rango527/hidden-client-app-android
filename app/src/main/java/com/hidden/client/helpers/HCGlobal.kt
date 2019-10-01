package com.hidden.client.helpers

import android.app.Activity
import android.content.Context
import android.util.Log

class HCGlobal private constructor(context: Context) {

    private val IS_DEBUG = true
    private val LOG_TAG: String = "HiddenClient"

    public lateinit var g_currentActivity: Activity;

    init {

    }

    val g_name: String = "Akio"

    companion object : HCSingletonHolder<HCGlobal, Context>(::HCGlobal)

    fun HCLog(message: String) {
        if (IS_DEBUG)
            Log.d(this.LOG_TAG, message)
    }

    val IMAGE_TYPE_CIRCLE: Int = 0
    val IMAGE_TYPE_ROUNDED_RECTANGLE: Int = 1
}