package com.hidden.client.helpers

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val NAME = "HiddenClient"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val MY_ID = Pair("my_id", 0)
    private val MY_FULL_NAME = Pair("my_full_name", "")
    private val API_ACCESS_TOKEN = Pair("api_access_token", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var apiAccessToken: String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(API_ACCESS_TOKEN.first, API_ACCESS_TOKEN.second).toString()

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(API_ACCESS_TOKEN.first, value)
        }

    var myId: Int
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getInt(MY_ID.first, MY_ID.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putInt(MY_ID.first, value)
        }

    var myFullName: String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(MY_FULL_NAME.first, MY_FULL_NAME.second).toString()

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(MY_FULL_NAME.first, value)
        }
}