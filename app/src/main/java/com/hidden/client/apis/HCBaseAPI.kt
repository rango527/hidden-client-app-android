package com.hidden.client.apis

import android.util.Log

import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener

import org.json.JSONObject

internal object HCBaseAPI {

    var baseUrl = ""
    var callback: APICallback<*>? = null

    fun parseResp(url: String, obj: JSONObject, callback: ParseCallback) {

        try {
            if (obj.getBoolean("HasErrors")) {
                val errors = obj.getJSONArray("ObjectErrors")
                val errMsg = if (errors.length() == 0) "Server Error" else errors.getString(0)
                callback.onCompleted(null, errMsg)
                return
            }
            callback.onCompleted(obj.get("Data"), "")
        } catch (e: Exception) {
            e.printStackTrace()
            callback.onCompleted(null, "Server Error")
        }

    }

    internal interface ParseCallback {
        fun onCompleted(respObj: Any?, errMsg: String)
    }

    internal abstract class CSJSONObjListener : JSONObjectRequestListener {

        override fun onResponse(response: JSONObject) {

        }

        override fun onError(anError: ANError) {
            callback!!.onCompleted(anError.errorDetail)
        }
    }
}
