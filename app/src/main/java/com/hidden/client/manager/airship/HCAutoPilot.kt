package com.hidden.client.manager.airship

import android.content.Context
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.hidden.client.R
import com.urbanairship.AirshipConfigOptions
import com.urbanairship.BuildConfig
import com.urbanairship.UAirship
import java.security.AccessController.getContext


//class SampleAutopilot : AutoPilot() {

//    fun onAirshipReady(@NonNull airship: UAirship) {
//        airship.pushManager.userNotificationsEnabled = true
//
//        // Additional Airship SDK setup
//    }
//
//    fun createAirshipConfigOptions(@NonNull context: Context): AirshipConfigOptions {
//
//        return AirshipConfigOptions.Builder()
//            .setDevelopmentAppKey("Your Development App Key")
//            .setDevelopmentAppSecret("Your Development App Secret")
//            .setProductionAppKey("Your Production App Key")
//            .setProductionAppSecret("Your Production App Secret")
//            .setInProduction(!BuildConfig.DEBUG)
////            .setNotificationIcon(R.drawable.ic_l)
//            .setNotificationAccentColor(context.resources.getColor(R.color.colorRed_1))
//            .setNotificationChannel("customChannel")
//            .build()
//    }
//}