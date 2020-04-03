/* Copyright Airship and Contributors */

package com.urbanairship.analytics;

import androidx.annotation.NonNull;

import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.BaseTestCase;
import com.urbanairship.TestActivityMonitor;
import com.urbanairship.TestApplication;
import com.urbanairship.analytics.data.EventManager;
import com.urbanairship.channel.AirshipChannel;
import com.urbanairship.job.JobDispatcher;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
public class AnalyticsTest extends BaseTestCase {

    private Analytics analytics;
    private JobDispatcher mockJobDispatcher;
    private EventManager mockEventManager;
    private AirshipChannel mockChannel;

    @Before
    public void setup() {
        this.mockJobDispatcher = Mockito.mock(JobDispatcher.class);
        this.mockEventManager = Mockito.mock(EventManager.class);
        this.mockChannel = Mockito.mock(AirshipChannel.class);

        AirshipConfigOptions airshipConfigOptions = new AirshipConfigOptions.Builder()
                .setDevelopmentAppKey("appKey")
                .setDevelopmentAppSecret("appSecret")
                .build();

        this.analytics = Analytics.newBuilder(TestApplication.getApplication())
                                  .setActivityMonitor(new TestActivityMonitor())
                                  .setConfigOptions(airshipConfigOptions)
                                  .setPreferenceDataStore(TestApplication.getApplication().preferenceDataStore)
                                  .setEventManager(mockEventManager)
                                  .setAirshipChannel(mockChannel)
                                  .setExecutor(new Executor() {
                                      @Override
                                      public void execute(@NonNull Runnable runnable) {
                                          runnable.run();
                                      }
                                  })
                                  .build();

        analytics.init();
    }

    /**
     * Test that a session id is created when analytics is created
     */
    @Test
    public void testOnCreate() {
        assertNotNull("Session id should generate on create", analytics.getSessionId());
    }

    /**
     * Test that when the app goes into the foreground, a new
     * session id is created, a broadcast is sent for foreground, isAppForegrounded
     * is set to true, and a foreground event job is dispatched.
     */
    @Test
    public void testOnForeground() {
        // Start analytics in the background
        analytics.onBackground(0);

        assertFalse(analytics.isAppInForeground());

        // Grab the session id to compare it to a new session id
        String sessionId = analytics.getSessionId();

        analytics.onForeground(0);

        // Verify that we generate a new session id
        assertNotNull(analytics.getSessionId());
        assertNotSame("A new session id should be generated on foreground", analytics.getSessionId(), sessionId);
    }

    /**
     * Test that when the app goes into the background, the conversion send id is
     * cleared, a background event is sent to be added, and a broadcast for background
     * is sent.
     */
    @Test
    public void testOnBackground() {
        // Start analytics in the foreground
        analytics.setConversionSendId("some-id");

        analytics.onBackground(0);

        // Verify that we clear the conversion send id
        assertNull("App background should clear the conversion send id", analytics.getConversionSendId());

        // Verify that a job to add a background event is dispatched
        verify(mockEventManager).addEvent(Mockito.any(AppBackgroundEvent.class), Mockito.anyString());
    }

    /**
     * Test setting the conversion conversion send id
     */
    @Test
    public void testSetConversionPushId() {
        analytics.setConversionSendId("some-id");
        assertEquals("Conversion send Id is unable to be set", analytics.getConversionSendId(), "some-id");

        analytics.setConversionSendId(null);
        assertNull("Conversion send Id is unable to be cleared", analytics.getConversionSendId());
    }

    /**
     * Test adding an event
     */
    @Test
    public void testAddEvent() {
        CustomEvent event = CustomEvent.newBuilder("cool").build();

        analytics.addEvent(event);

        verify(mockEventManager).addEvent(event, analytics.getSessionId());
    }

    /**
     * Test adding an event when analytics is disabled through airship config.
     */
    @Test
    public void testAddEventDisabledAnalyticsConfig() {
        AirshipConfigOptions options = new AirshipConfigOptions.Builder()
                .setDevelopmentAppKey("appKey")
                .setDevelopmentAppSecret("appSecret")
                .setAnalyticsEnabled(false)
                .build();

        this.analytics = Analytics.newBuilder(TestApplication.getApplication())
                                  .setActivityMonitor(new TestActivityMonitor())
                                  .setConfigOptions(options)
                                  .setPreferenceDataStore(TestApplication.getApplication().preferenceDataStore)
                                  .setEventManager(mockEventManager)
                                  .setAirshipChannel(mockChannel)
                                  .build();

        analytics.addEvent(new AppForegroundEvent(100));
        verifyZeroInteractions(mockEventManager);
    }

    /**
     * Test adding an event when analytics is disabled
     */
    @Test
    public void testAddEventDisabledAnalytics() {
        analytics.setEnabled(false);
        analytics.addEvent(new AppForegroundEvent(100));
        verify(mockEventManager, never()).addEvent(Mockito.any(AppForegroundEvent.class), Mockito.anyString());
    }

    /**
     * Test adding a null event
     */
    @Test
    public void testAddNullEvent() {
        analytics.addEvent(null);
        verifyZeroInteractions(mockEventManager);
    }

    /**
     * Test adding an invalid event
     */
    @Test
    public void testAddInvalidEvent() {
        Event event = Mockito.mock(Event.class);
        when(event.getEventId()).thenReturn("event-id");
        when(event.getType()).thenReturn("event-type");
        when(event.createEventPayload(Mockito.anyString())).thenReturn("event-data");
        when(event.getEventId()).thenReturn("event-id");
        when(event.getTime()).thenReturn("1000");
        when(event.getPriority()).thenReturn(Event.HIGH_PRIORITY);

        when(event.isValid()).thenReturn(false);

        analytics.addEvent(event);
        verifyZeroInteractions(mockJobDispatcher);
    }

    /**
     * Test disabling analytics should start dispatch a job to delete all events.
     */
    @Test
    public void testDisableAnalytics() {
        analytics.setEnabled(false);
        verify(mockEventManager).deleteEvents();
    }

    /**
     * Test editAssociatedIdentifiers dispatches a job to add a new associate_identifiers event.
     */
    @Test
    public void testEditAssociatedIdentifiers() {
        analytics.editAssociatedIdentifiers()
                 .addIdentifier("customKey", "customValue")
                 .apply();

        // Verify we started an add event job
        verify(mockEventManager).addEvent(Mockito.any(AssociateIdentifiersEvent.class), Mockito.anyString());

        // Verify identifiers are stored
        AssociatedIdentifiers storedIds = analytics.getAssociatedIdentifiers();
        assertEquals(storedIds.getIds().get("customKey"), "customValue");
        assertEquals(storedIds.getIds().size(), 1);
    }

    /**
     * Test editAssociatedIdentifiers doesn't dispatch a job when adding a duplicate associate_identifier.
     */
    @Test
    public void testEditDuplicateAssociatedIdentifiers() {
        analytics.editAssociatedIdentifiers()
                 .addIdentifier("customKey", "customValue")
                 .apply();

        // Verify we started an add event job
        verify(mockEventManager).addEvent(Mockito.any(AssociateIdentifiersEvent.class), Mockito.anyString());

        // Edit with a duplicate identifier
        analytics.editAssociatedIdentifiers()
                 .addIdentifier("customKey", "customValue")
                 .apply();

        // Verify we don't add an event more than once
        verify(mockEventManager, times(1)).addEvent(Mockito.any(AssociateIdentifiersEvent.class), Mockito.anyString());
    }

    /**
     * Test that tracking event adds itself on background
     */
    @Test
    public void testTrackingEventBackground() {
        analytics.setEnabled(true);

        analytics.trackScreen("test_screen");

        // Make call to background
        analytics.onBackground(0);

        // Verify we started an add event job
        verify(mockEventManager).addEvent(Mockito.argThat(new ArgumentMatcher<Event>() {
            @Override
            public boolean matches(Event argument) {
                return argument.getEventData().opt("screen").optString().equals("test_screen");
            }
        }), Mockito.anyString());
    }

    /**
     * Test that tracking event adds itself upon adding a new screen
     */
    @Test
    public void testTrackingEventAddNewScreen() {
        analytics.trackScreen("test_screen_1");

        // Add another screen
        analytics.trackScreen("test_screen_2");

        // Verify we started an add event job
        verify(mockEventManager).addEvent(Mockito.argThat(new ArgumentMatcher<Event>() {
            @Override
            public boolean matches(Event argument) {
                return argument.getEventData().opt("screen").optString().equals("test_screen_1");
            }
        }), Mockito.anyString());
    }

    /**
     * Test that tracking event ignores duplicate tracking calls for same screen
     */
    @Test
    public void testTrackingEventAddSameScreen() {
        analytics.setEnabled(true);

        analytics.trackScreen("test_screen_1");

        // Add another screen
        analytics.trackScreen("test_screen_1");

        // Verify no jobs were created for the event
        verifyZeroInteractions(mockJobDispatcher);
    }

    /**
     * Test that SDK extensions are registered correctly
     */
    @Test
    public void testSDKExtensions() {
        analytics.registerSDKExtension("cordova", "1.2.3");
        analytics.registerSDKExtension("unity", "5,.6,.7,,,");

        Map<String, String> expectedExtensions = new HashMap<>();
        expectedExtensions.put("cordova", "1.2.3");
        expectedExtensions.put("unity", "5.6.7");

        assertEquals(expectedExtensions, analytics.getExtensions());
    }

}
