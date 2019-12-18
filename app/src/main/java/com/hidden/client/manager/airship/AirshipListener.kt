package com.hidden.client.manager.airship

import android.util.Log
import androidx.annotation.NonNull
import com.urbanairship.channel.AirshipChannelListener;
import com.urbanairship.push.NotificationActionButtonInfo;
import com.urbanairship.push.NotificationInfo;
import com.urbanairship.push.NotificationListener;
import com.urbanairship.push.PushListener;
import com.urbanairship.push.PushMessage;
import com.urbanairship.push.PushTokenListener;

/**
 * Listener for push, notifications, and registrations events.
 */
class AirshipListener : PushListener, NotificationListener, PushTokenListener,
    AirshipChannelListener {

    override fun onNotificationPosted(@NonNull notificationInfo: NotificationInfo) {
        Log.i(TAG, "Notification posted: $notificationInfo")
    }

    override fun onNotificationOpened(@NonNull notificationInfo: NotificationInfo): Boolean {
        Log.i(TAG, "Notification opened: $notificationInfo")

        // Return false here to allow Airship to auto launch the launcher
        // activity for foreground notification action buttons
        return false
    }

    override fun onNotificationForegroundAction(@NonNull notificationInfo: NotificationInfo, @NonNull actionButtonInfo: NotificationActionButtonInfo): Boolean {
        Log.i(TAG, "Notification action: $notificationInfo $actionButtonInfo")

        // Return false here to allow Airship to auto launch the launcher
        // activity for foreground notification action buttons
        return false
    }

    override fun onNotificationBackgroundAction(@NonNull notificationInfo: NotificationInfo, @NonNull actionButtonInfo: NotificationActionButtonInfo) {
        Log.i(TAG, "Notification action: $notificationInfo $actionButtonInfo")
    }

    override fun onNotificationDismissed(@NonNull notificationInfo: NotificationInfo) {
        Log.i(
            TAG,
            "Notification dismissed. Alert: " + notificationInfo.message.alert + ". Notification ID: " + notificationInfo.notificationId
        )
    }

    override fun onPushReceived(@NonNull message: PushMessage, notificationPosted: Boolean) {
        Log.i(
            TAG,
            "Received push message. Alert: " + message.alert + ". Posted notification: " + notificationPosted
        )
    }

    override fun onChannelCreated(@NonNull channelId: String) {
        Log.i(TAG, "Channel created $channelId")
    }

    override fun onChannelUpdated(@NonNull channelId: String) {
        Log.i(TAG, "Channel updated $channelId")
    }

    override fun onPushTokenUpdated(@NonNull token: String) {
        Log.i(TAG, "Push token updated $token")
    }

    companion object {

        private val TAG = "AirshipListener"
    }

}