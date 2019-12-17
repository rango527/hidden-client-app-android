/* Copyright Airship and Contributors */

package com.urbanairship.push.notifications;

import android.app.Notification;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.push.PushMessage;
import com.urbanairship.util.UAStringUtil;

/**
 * The default notification factory.
 * <p>
 * Notifications generated by this factory use the standard Android notification
 * layout and defaults to the BigTextStyle.
 * <p>
 * To customize the factory, override {@link #extendBuilder(NotificationCompat.Builder, PushMessage, int)}.
 *
 * @deprecated Use {@link AirshipNotificationProvider} instead.
 */
@Deprecated
public class DefaultNotificationFactory extends NotificationFactory {

    /**
     * Default constructor.
     *
     * @param context The application context
     */
    public DefaultNotificationFactory(@NonNull Context context) {
        super(context);
    }

    /**
     * Creates the default notification factory with settings applied from AirshipConfig options.
     *
     * @param context The application context.
     * @param options The airship config options.
     * @return The default notification factory.
     */
    @NonNull
    public static DefaultNotificationFactory newFactory(@NonNull Context context, @NonNull AirshipConfigOptions options) {
        DefaultNotificationFactory factory = new DefaultNotificationFactory(context);

        if (options.notificationIcon != 0) {
            factory.setSmallIconId(options.notificationIcon);
        }

        factory.setColor(options.notificationAccentColor);
        factory.setNotificationChannel(options.notificationChannel);
        factory.setLargeIcon(options.notificationLargeIcon);

        return factory;
    }

    @Nullable
    @Override
    public final Notification createNotification(@NonNull PushMessage message, int notificationId) {
        if (UAStringUtil.isEmpty(message.getAlert())) {
            return null;
        }

        NotificationCompat.Builder builder = createNotificationBuilder(message, notificationId, new NotificationCompat.BigTextStyle().bigText(message.getAlert()));
        return extendBuilder(builder, message, notificationId).build();
    }

    /**
     * Called to apply any final overrides to the builder before the notification is built.
     *
     * @param builder The notification builder.
     * @param message The push message.
     * @param notificationId The notification ID.
     * @return The notification builder.
     */
    @NonNull
    public NotificationCompat.Builder extendBuilder(@NonNull NotificationCompat.Builder builder, @NonNull PushMessage message, int notificationId) {
        return builder;
    }

}
