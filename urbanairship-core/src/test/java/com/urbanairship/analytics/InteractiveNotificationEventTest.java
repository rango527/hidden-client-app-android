/* Copyright Airship and Contributors */

package com.urbanairship.analytics;

import android.os.Bundle;

import com.urbanairship.BaseTestCase;
import com.urbanairship.push.NotificationActionButtonInfo;
import com.urbanairship.push.NotificationInfo;
import com.urbanairship.push.PushMessage;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.urbanairship.analytics.EventTestUtils.validateEventValue;
import static com.urbanairship.analytics.EventTestUtils.validateNestedEventValue;
import static org.mockito.Mockito.when;

public class InteractiveNotificationEventTest extends BaseTestCase {

    private PushMessage mockPushMessage;

    @Before
    public void setUp() {
        mockPushMessage = Mockito.mock(PushMessage.class);
        when(mockPushMessage.getSendId()).thenReturn("send id");
        when(mockPushMessage.getInteractiveNotificationType()).thenReturn("interactive notification type");
    }

    /**
     * Test the interactive notification event data is populated properly.
     */
    @Test
    public void testData() throws JSONException {
        NotificationInfo notificationInfo = new NotificationInfo(mockPushMessage, 100, "tag");
        NotificationActionButtonInfo actionButtonInfo = new NotificationActionButtonInfo("button id", false, null, "button description");

        InteractiveNotificationEvent event = new InteractiveNotificationEvent(notificationInfo, actionButtonInfo);

        validateEventValue(event, "button_id", "button id");
        validateEventValue(event, "button_description", "button description");
        validateEventValue(event, "button_group", "interactive notification type");
        validateEventValue(event, "foreground", false);
        validateEventValue(event, "send_id", "send id");
    }

    /**
     * Test the interactive notification event data is populated properly.
     */
    @Test
    public void testDatWithRemoteInput() throws JSONException {
        Bundle remoteInput = new Bundle();
        remoteInput.putCharSequence("input_one", "cool");
        remoteInput.putCharSequence("input_two", "story");

        NotificationInfo notificationInfo = new NotificationInfo(mockPushMessage, 100, "tag");
        NotificationActionButtonInfo actionButtonInfo = new NotificationActionButtonInfo("button id", false, remoteInput, "button description");

        InteractiveNotificationEvent event = new InteractiveNotificationEvent(notificationInfo, actionButtonInfo);

        validateEventValue(event, "button_id", "button id");
        validateEventValue(event, "button_description", "button description");
        validateEventValue(event, "button_group", "interactive notification type");
        validateEventValue(event, "foreground", false);
        validateEventValue(event, "send_id", "send id");
        validateNestedEventValue(event, "user_input", "input_one", "cool");
        validateNestedEventValue(event, "user_input", "input_two", "story");
    }

}