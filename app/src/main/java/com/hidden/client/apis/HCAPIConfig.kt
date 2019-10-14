package com.hidden.client.apis

import android.app.Activity
import android.content.Context
import android.util.Log
import com.hidden.client.helpers.HCSingletonHolder

class HCAPIConfig private constructor(context: Context) {

    companion object : HCSingletonHolder<HCAPIConfig, Context>(::HCAPIConfig)

    val baseUrl = "https://staging-api.hidden.io"

}