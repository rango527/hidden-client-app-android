/* Copyright Airship and Contributors */

package com.urbanairship.location;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.urbanairship.Logger;

/**
 * A ProximityRegion defines a proximity region with an identifier, major, minor
 * and optional latitude, longitude and RSSI.
 */
public class ProximityRegion {

    /**
     * The maximum proximity region major and minor value.
     */
    private static final int MAX_MAJOR_MINOR_VALUE = 65535;

    /**
     * The maximum RSSI for a proximity region in dBm.
     */
    private static final int MAX_RSSI = 100;

    /**
     * The minimum RSSI for a proximity region in dBm.
     */
    private static final int MIN_RSSI = -100;

    /**
     * The proximity region's identifier.
     */
    private final String proximityId;

    /**
     * The proximity region's major.
     */
    private final int major;

    /**
     * The proximity region's minor.
     */
    private final int minor;

    /**
     * The proximity region's latitude in degrees.
     */
    private Double latitude;

    /**
     * The proximity region's longitude in degrees.
     */
    private Double longitude;

    /**
     * The proximity region's received signal strength indication in dBm.
     */
    private Integer rssi;

    /**
     * Constructor for creating proximity region.
     *
     * @param proximityId The ID of the region object.
     * @param major The major.
     * @param minor The minor.
     */
    public ProximityRegion(@NonNull String proximityId,
                           @IntRange(from = 0, to = MAX_MAJOR_MINOR_VALUE) int major,
                           @IntRange(from = 0, to = MAX_MAJOR_MINOR_VALUE) int minor) {

        this.proximityId = proximityId;
        this.major = major;
        this.minor = minor;
    }

    /**
     * Sets the proximity region's latitude and longitude.
     *
     * @param latitude The proximity region's latitude.
     * @param longitude The proximity region's longitude.
     */
    public void setCoordinates(@Nullable @FloatRange(from = RegionEvent.MIN_LATITUDE, to = RegionEvent.MAX_LATITUDE) Double latitude,
                               @Nullable @FloatRange(from = RegionEvent.MIN_LONGITUDE, to = RegionEvent.MAX_LONGITUDE) Double longitude) {

        if (latitude == null || longitude == null) {
            this.latitude = null;
            this.longitude = null;
            return;
        }

        if (!RegionEvent.regionEventLatitudeIsValid(latitude)) {
            Logger.error("The latitude must be greater than or equal to %s and less than or equal to %s degrees.", RegionEvent.MIN_LATITUDE, RegionEvent.MAX_LATITUDE);
            this.latitude = null;
            return;
        }

        if (!RegionEvent.regionEventLongitudeIsValid(longitude)) {
            Logger.error("The longitude must be greater than or equal to %s and less than or equal to %s degrees.", RegionEvent.MIN_LONGITUDE, RegionEvent.MAX_LONGITUDE);
            this.longitude = null;
            return;
        }

        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Sets the proximity region's received signal strength indication.
     *
     * @param rssi The proximity region's received signal strength indication.
     */
    public void setRssi(@Nullable @IntRange(from = MIN_RSSI, to = MAX_RSSI) Integer rssi) {
        if (rssi == null) {
            this.rssi = null;
            return;
        }

        if (rssi > MAX_RSSI || rssi < MIN_RSSI) {
            Logger.error("The rssi must be greater than or equal to %s and less than or equal to %s dBm.", MIN_RSSI, MAX_RSSI);
            this.rssi = null;
            return;
        }

        this.rssi = rssi;
    }

    /**
     * Gets the proximity region's proximity ID.
     *
     * @return The proximity ID.
     */
    @NonNull
    public String getProximityId() {
        return this.proximityId;
    }

    /**
     * Gets the proximity region's major.
     *
     * @return The major.
     */
    public int getMajor() {
        return this.major;
    }

    /**
     * Gets the proximity region's minor.
     *
     * @return The minor.
     */
    public int getMinor() {
        return this.minor;
    }

    /**
     * Gets the proximity region's latitude in degrees.
     *
     * @return The latitude.
     */
    @Nullable
    public Double getLatitude() {
        return this.latitude;
    }

    /**
     * Gets the proximity region's longitude in degrees.
     *
     * @return The longitude.
     */
    @Nullable
    public Double getLongitude() {
        return this.longitude;
    }

    /**
     * Gets the proximity region's RSSI in dBm.
     *
     * @return The RSSI.
     */
    @Nullable
    public Integer getRssi() {
        return this.rssi;
    }

    /**
     * Validates the proximity region object.
     *
     * @return True if the proximity region is valid, false otherwise.
     */
    public boolean isValid() {
        //noinspection ConstantConditions
        if (proximityId == null) {
            Logger.error("The proximity ID must not be null.");
            return false;
        }

        if (!RegionEvent.regionEventCharacterCountIsValid(proximityId)) {
            Logger.error("The proximity ID must not be greater than %s or less than %s characters in length.", RegionEvent.MAX_CHARACTER_LENGTH, 1);
            return false;
        }

        if (major > MAX_MAJOR_MINOR_VALUE || major < 0) {
            Logger.error("The major must not be greater than " + MAX_MAJOR_MINOR_VALUE +
                    " or less than " + 0 + ".");
            return false;
        }

        if (minor > MAX_MAJOR_MINOR_VALUE || minor < 0) {
            Logger.error("The minor must not be greater than %s or less than %s.", MAX_MAJOR_MINOR_VALUE, 0);
            return false;
        }

        return true;
    }

}

