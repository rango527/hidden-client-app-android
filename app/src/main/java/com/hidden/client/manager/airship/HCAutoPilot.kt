package com.hidden.client.manager.airship

import android.content.Context
import com.urbanairship.UAirship
import com.urbanairship.AirshipConfigOptions
import android.content.Intent
import android.net.Uri
import androidx.annotation.Nullable
import com.hidden.client.helpers.AppPreferences
import com.urbanairship.Autopilot

/**
* Autopilot that enables user notifications on first run.
*/
class HCAutoPilot : Autopilot() {

    override fun onAirshipReady(airship: UAirship) {

        val isFirstRun = AppPreferences.firstRunKey
        if (isFirstRun) {
            AppPreferences.firstRunKey = false

            // Enable user notifications on first run
            airship.pushManager.userNotificationsEnabled = true
        }

        airship.messageCenter.setOnShowMessageCenterListener { messageId ->
            // Use an implicit navigation deep link for now as explicit deep links are broken
            // with multi navigation host fragments
            val uri: Uri = if (messageId != null) {
                Uri.parse("vnd.urbanairship.sample://deepLink/inbox/message/$messageId")
            } else {
                Uri.parse("vnd.urbanairship.sample://deepLink/inbox")
            }

            val intent = Intent(Intent.ACTION_VIEW, uri)
                .setPackage(UAirship.getPackageName())
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            UAirship.getApplicationContext().startActivity(intent)
            true
        }

        val airshipListener = AirshipListener()
        airship.pushManager.addPushListener(airshipListener)
        airship.pushManager.addPushTokenListener(airshipListener)
        airship.pushManager.setNotificationListener(airshipListener)
        airship.channel.addChannelListener(airshipListener)
    }

    @Nullable
    override fun createAirshipConfigOptions(context: Context): AirshipConfigOptions? {
        /*
          Optionally, customize your config at runtime:

             AirshipConfigOptions options = new AirshipConfigOptions.Builder()
                    .setInProduction(!BuildConfig.DEBUG)
                    .setDevelopmentAppKey("Your Development App Key")
                    .setDevelopmentAppSecret("Your Development App Secret")
                    .setProductionAppKey("Your Production App Key")
                    .setProductionAppSecret("Your Production App Secret")
                    .setNotificationAccentColor(ContextCompat.getColor(context, R.color.color_accent))
                    .setNotificationIcon(R.drawable.ic_airship)
                    .build();

            return options;
         */

        // defaults to loading config from airshipconfig.properties file
        return super.createAirshipConfigOptions(context)
    }

}