/* Copyright Airship and Contributors */

package com.urbanairship;

import android.graphics.Color;
import android.util.Log;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the parsing of each type of configuration
 * value from a Java-style properties file.
 */
public class AirshipConfigOptionsTest extends BaseTestCase {

    private static final String TEST_PROPERTIES_FILE = "valid.properties";
    private static final String INVALID_PROPERTIES_FILE = "invalid.properties";

    /**
     * This test verifies the applyProperties method can parse different types
     */
    @Test
    public void testLoadFromProperties() throws IOException {
        AirshipConfigOptions production = new AirshipConfigOptions.Builder()
                .applyProperties(getApplication(), getProperties(TEST_PROPERTIES_FILE))
                .build();

        AirshipConfigOptions development = new AirshipConfigOptions.Builder()
                .applyProperties(getApplication(), getProperties(TEST_PROPERTIES_FILE))
                .setInProduction(false)
                .build();

        assertEquals("devAppKey", development.appKey);
        assertEquals("devAppSecret", development.appSecret);

        assertEquals("prodAppKey", production.appKey);
        assertEquals("prodAppSecret", production.appSecret);
        assertEquals("https://test.host.url.com/", production.deviceUrl);
        assertEquals("https://test.analytics.url.com/", production.analyticsUrl);
        assertEquals(Arrays.asList("GCM_TRANSPORT"), production.allowedTransports);
        assertEquals("https://first.whitelist.url.com/", production.whitelist.get(0));
        assertEquals("https://second.whitelist.url.com/", production.whitelist.get(1));
        assertTrue(production.inProduction);
        assertFalse(production.analyticsEnabled);
        assertEquals(2700, production.backgroundReportingIntervalMS);
        assertTrue(production.clearNamedUser);
        assertFalse(production.autoLaunchApplication);
        assertTrue(production.channelCreationDelayEnabled);
        assertFalse(production.channelCaptureEnabled);
        assertEquals(Log.VERBOSE, production.logLevel);
        assertEquals(R.drawable.ua_ic_urbanairship_notification, production.notificationIcon);
        assertEquals(Color.parseColor("#ff0000"), production.notificationAccentColor);
        assertEquals("https://test.wallet.url.com/", production.walletUrl);
        assertEquals("test_channel", production.notificationChannel);
        assertEquals("https://play.google.com/store/apps/topic?id=editors_choice", production.appStoreUri.toString());
    }

    /**
     * This test loads invalid values and verify the property value is set to default
     */
    @Test
    public void testInvalidOptions() throws IOException {
        AirshipConfigOptions development = new AirshipConfigOptions.Builder()
                .applyProperties(getApplication(), getProperties(INVALID_PROPERTIES_FILE))
                .setInProduction(false)
                .build();

        assertEquals(Log.DEBUG, development.logLevel);

        AirshipConfigOptions production = new AirshipConfigOptions.Builder()
                .applyProperties(getApplication(), getProperties(INVALID_PROPERTIES_FILE))
                .setInProduction(true)
                .build();
        assertEquals(Log.ERROR, production.logLevel);

        assertEquals(0, production.notificationAccentColor);
        assertEquals(0, production.notificationIcon);
    }

    @Test
    public void testDevelopmentFcmSenderId() {
        AirshipConfigOptions aco = new AirshipConfigOptions.Builder()
                .setDevelopmentAppKey("appKey")
                .setDevelopmentAppSecret("appSecret")
                .setProductionAppSecret("appSecret")
                .setProductionAppKey("appKey")
                .setInProduction(false)
                .setDevelopmentFcmSenderId("dev fcm sender ID")
                .setProductionFcmSenderId("prod fcm sender ID")
                .setFcmSenderId("fcm sender ID")
                .build();

        assertEquals("dev fcm sender ID", aco.fcmSenderId);
    }

    @Test
    public void testProductionFcmSenderId() {
        AirshipConfigOptions aco = new AirshipConfigOptions.Builder()
                .setDevelopmentAppKey("appKey")
                .setDevelopmentAppSecret("appSecret")
                .setProductionAppSecret("appSecret")
                .setProductionAppKey("appKey")
                .setInProduction(true)
                .setDevelopmentFcmSenderId("dev fcm sender ID")
                .setProductionFcmSenderId("prod fcm sender ID")
                .setFcmSenderId("fcm sender ID")
                .build();

        assertEquals("prod fcm sender ID", aco.fcmSenderId);
    }

    @Test
    public void testCommonFcmSenderId() {
        AirshipConfigOptions aco = new AirshipConfigOptions.Builder()
                .setDevelopmentAppKey("appKey")
                .setDevelopmentAppSecret("appSecret")
                .setProductionAppSecret("appSecret")
                .setProductionAppKey("appKey")
                .setInProduction(true)
                .setDevelopmentFcmSenderId("dev fcm sender ID")
                .setFcmSenderId("fcm sender ID")
                .build();

        assertEquals("fcm sender ID", aco.fcmSenderId);
    }

    @Test
    public void testValidate() {
        AirshipConfigOptions valid = AirshipConfigOptions.newBuilder()
                                                         .setAppKey("-----1abc123_-90000000")
                                                         .setAppSecret("0A00000000000000000000")
                                                         .build();

        valid.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateChecksKey() {
        AirshipConfigOptions valid = AirshipConfigOptions.newBuilder()
                                                         .setAppKey("0A00000000000") // not enough characters
                                                         .build();

        valid.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateChecksSecret() {
        AirshipConfigOptions valid = AirshipConfigOptions.newBuilder()
                                                         .setAppSecret("0A00000000000000000000EXTRA") // too many characters
                                                         .build();

        valid.validate();
    }

    @Test
    public void testEuCloudSite() {
        AirshipConfigOptions configOptions = AirshipConfigOptions.newBuilder()
                                                                 .setSite(AirshipConfigOptions.SITE_EU)
                                                                 .build();

        assertEquals(configOptions.analyticsUrl, "https://combine.asnapieu.com/");
        assertEquals(configOptions.deviceUrl, "https://device-api.asnapieu.com/");
        assertEquals(configOptions.remoteDataUrl, "https://remote-data.asnapieu.com/");
        assertEquals(configOptions.walletUrl, "https://wallet-api.asnapieu.com");
    }

    @Test
    public void testUsCloudSite() {
        AirshipConfigOptions configOptions = AirshipConfigOptions.newBuilder()
                                                                 .setSite(AirshipConfigOptions.SITE_US)
                                                                 .build();

        assertEquals(configOptions.analyticsUrl, "https://combine.urbanairship.com/");
        assertEquals(configOptions.deviceUrl, "https://device-api.urbanairship.com/");
        assertEquals(configOptions.remoteDataUrl, "https://remote-data.urbanairship.com/");
        assertEquals(configOptions.walletUrl, "https://wallet-api.urbanairship.com");
    }


    @Test
    public void testUrlOverrides() {
        AirshipConfigOptions configOptions = AirshipConfigOptions.newBuilder()
                                                                 .setSite(AirshipConfigOptions.SITE_EU)
                                                                 .setAnalyticsUrl("some-analytics-url")
                                                                 .setDeviceUrl("some-device-url")
                                                                 .setRemoteDataUrl("some-remote-data-url")
                                                                 .setWalletUrl("some-wallet-url")
                                                                 .build();

        assertEquals(configOptions.analyticsUrl, "some-analytics-url");
        assertEquals(configOptions.deviceUrl, "some-device-url");
        assertEquals(configOptions.remoteDataUrl, "some-remote-data-url");
        assertEquals(configOptions.walletUrl, "some-wallet-url");
    }

    Properties getProperties(String file) throws IOException {
        InputStream stream = null;
        try {
            stream = getClass().getClassLoader().getResourceAsStream(file);
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

}
