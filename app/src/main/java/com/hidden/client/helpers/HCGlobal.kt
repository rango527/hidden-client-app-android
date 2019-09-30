package com.hidden.client.helpers

import android.content.Context
import android.util.Log

class HCGlobal private constructor(context: Context) {

    private val LOG_TAG: String = "HiddenClient"

    init {

    }

    companion object : HCSingletonHolder<HCGlobal, Context>(::HCGlobal)

    fun HCLog(message: String) {
        Log.d(this.LOG_TAG, message)
    }
}