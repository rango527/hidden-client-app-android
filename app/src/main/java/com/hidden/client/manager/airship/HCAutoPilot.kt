package com.hidden.client.manager.airship

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.Nullable
import androidx.core.app.NotificationManagerCompat
import com.hidden.client.helpers.AppPreferences
import com.urbanairship.AirshipConfigOptions
import com.urbanairship.Autopilot
import com.urbanairship.UAirship
import com.urbanairship.push.notifications.NotificationChannelCompat

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

        val pushId = """client-${AppPreferences.myId}"""
        if (airship.namedUser.id != pushId) {
            airship.namedUser.id = """client-${AppPreferences.myId}"""
        }

        val channelCompat = NotificationChannelCompat(
            "customChannel",
            "hiddenClient",
            NotificationManagerCompat.IMPORTANCE_DEFAULT
        )

        airship.pushManager.notificationChannelRegistry.createNotificationChannel(channelCompat);

        val airshipListener = AirshipListener()
        airship.pushManager.addPushListener(airshipListener)
        airship.pushManager.addPushTokenListener(airshipListener)
        airship.pushManager.setNotificationListener(airshipListener)
        airship.channel.addChannelListener(airshipListener)

        val  deepLinkListener = DeepLinkListener()
        airship.deepLinkListener = deepLinkListener
    }

    @Nullable
    override fun createAirshipConfigOptions(context: Context): AirshipConfigOptions? {

        //  Optionally, customize your config at runtime:
//        val options: AirshipConfigOptions = AirshipConfigOptions.Builder()
//            .setInProduction(!BuildConfig.DEBUG)
//            .setDevelopmentAppKey("a29nAUNaRTWFfLIONPBjnQ")
//            .setDevelopmentAppSecret("CtzQWHO9ScivFGT-DvT2Ow")
//            .setProductionAppKey("wxJmKXLvQFO16rFAd-M6SA")
//            .setProductionAppSecret("ao-foeKzTV-v6dlcH0KiLw")
//            .setFcmSenderId("739061058691")
//            .setNotificationAccentColor(ContextCompat.getColor(context, R.color.colorCyan_1))
//            .setNotificationIcon(R.drawable.ic_checked)
//            .setNotificationChannel("customChannel")
//            .build();
//
//        return options;

        // defaults to loading config from airshipconfig.properties file
        return super.createAirshipConfigOptions(context)
    }

}