package com.hidden.client.helpers

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val NAME = "HiddenClient"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val MY_ID = Pair("my_id", 0)
    private val IS_USER_MANAGER = Pair("is_user_manager", false)
    private val MY_FULL_NAME = Pair("my_full_name", "")
    private val API_ACCESS_TOKEN = Pair("api_access_token", "")

    // For Push Notification (UrbanAirship)
    private val FIRST_RUN_KEY = Pair("first_run_key", true)
    private val NO_BACKUP_PREFERENCES = Pair("no_backup_preferences", "com.hidden.client.manager.airship.no_backup")

    //Process Tip View
    private val PROCESS_SETTING_TIP_CHECKED = Pair("process_setting_tip_checked", false)

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

    var isUserManager: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_USER_MANAGER.first, IS_USER_MANAGER.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_USER_MANAGER.first, value)
        }

    var myFullName: String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(MY_FULL_NAME.first, MY_FULL_NAME.second).toString()

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(MY_FULL_NAME.first, value)
        }

    var firstRunKey: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(FIRST_RUN_KEY.first, FIRST_RUN_KEY.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(FIRST_RUN_KEY.first, value)
        }

    var noBackupPreferences: String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(NO_BACKUP_PREFERENCES.first, NO_BACKUP_PREFERENCES.second).toString()

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(NO_BACKUP_PREFERENCES.first, value)
        }

    var processSettingTipChecked: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(PROCESS_SETTING_TIP_CHECKED.first, PROCESS_SETTING_TIP_CHECKED.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(PROCESS_SETTING_TIP_CHECKED.first, value)
        }
}