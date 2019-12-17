/* Copyright Airship and Contributors */

package com.urbanairship.location;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.urbanairship.Logger;
import com.urbanairship.json.JsonException;
import com.urbanairship.json.JsonMap;
import com.urbanairship.json.JsonSerializable;
import com.urbanairship.json.JsonValue;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * High level location requirements to be used for UALocationManager and service.
 * <p>
 * The class is modeled after the LocationRequest for the Fused Location Provider,
 * but only supports a subset of the options.
 */
public class LocationRequestOptions implements JsonSerializable, Parcelable {

    /**
     * JSON key for the min distance.
     */
    @NonNull
    public static final String MIN_DISTANCE_KEY = "minDistance";

    /**
     * JSON key for the min time.
     */
    @NonNull
    public static final String MIN_TIME_KEY = "minTime";

    /**
     * JSON key for the request priority.
     */
    @NonNull
    public static final String PRIORITY_KEY = "priority";

    /**
     * Default minDistance in meters - 800 meters.
     */
    public static final float DEFAULT_UPDATE_INTERVAL_METERS = 800;

    /**
     * Default minTime in milliseconds - 5 minutes.
     */
    public static final long DEFAULT_UPDATE_INTERVAL_MILLISECONDS = 5 * 60 * 1000;

    /**
     * Default priority - PRIORITY_BALANCED_POWER_ACCURACY.
     */
    public static final int DEFAULT_REQUEST_PRIORITY = LocationRequestOptions.PRIORITY_BALANCED_POWER_ACCURACY;

    @IntDef({ PRIORITY_HIGH_ACCURACY, PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {}

    /**
     * Used with {@link com.urbanairship.location.LocationRequestOptions.Builder#setPriority(int)}
     * to request highest level of accuracy.
     * <p>
     * When used with the fused location provider it will request the highest
     * level of accuracy available.
     * <p>
     * When used with standard android location it will use Criteria.ACCURACY_FINE
     * and Criteria.POWER_HIGH.
     */
    public static final int PRIORITY_HIGH_ACCURACY = 1;

    /**
     * Used with {@link com.urbanairship.location.LocationRequestOptions.Builder#setPriority(int)}
     * to request balanced power and accuracy.
     * <p>
     * When used with the fused location provider it will use "block" level
     * accuracy, about 100 meter accuracy.
     * <p>
     * When used with standard android location it will use Criteria.ACCURACY_COARSE
     * and Criteria.POWER_MEDIUM.
     */
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 2;

    /**
     * Used with {@link com.urbanairship.location.LocationRequestOptions.Builder#setPriority(int)}
     * to request low power location updates.
     * <p>
     * When used with the fused location provider it will use "city" level
     * accuracy, about 10km accuracy.
     * <p>
     * When used with standard android location it will use Criteria.ACCURACY_COARSE
     * and Criteria.POWER_LOW.
     */
    public static final int PRIORITY_LOW_POWER = 3;

    /**
     * Used with {@link com.urbanairship.location.LocationRequestOptions.Builder#setPriority(int)}
     * to request location that requires no extra power consumption.
     * <p>
     * Warning: When used with the standard android location it will only use
     * the PASSIVE provider if available.  The passive provider requires ACCESS_FINE_LOCATION
     * permission.  If either the provider is missing or missing permissions location
     * will not be gathered.
     */
    public static final int PRIORITY_NO_POWER = 4;

    private final int priority;
    private final long minTime;
    private final float minDistance;

    /**
     * Creates a LocationRequestOptions object from a
     * {@link com.urbanairship.location.LocationRequestOptions.Builder}.
     *
     * @param builder The options builder.
     */
    private LocationRequestOptions(@NonNull Builder builder) {
        this(builder.priority, builder.minTime, builder.minDistance);
    }

    /**
     * Creates a LocationRequestOptions object from a parcel created
     * by {@link #CREATOR}.
     *
     * @param in The parcel.
     */
    private LocationRequestOptions(@NonNull Parcel in) {
        this(in.readInt(), in.readLong(), in.readFloat());
    }

    /**
     * Default constructor.
     *
     * @param priority The request priority.
     * @param minTime The request min update time in milliseconds.
     * @param minDistance The request min update distance in meters.
     */
    private LocationRequestOptions(int priority, long minTime, float minDistance) {
        this.priority = priority;
        this.minTime = minTime;
        this.minDistance = minDistance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(priority);
        dest.writeLong(minTime);
        dest.writeFloat(minDistance);
    }

    /**
     * Creates default request options with {@link #DEFAULT_REQUEST_PRIORITY},
     * {@link #DEFAULT_UPDATE_INTERVAL_MILLISECONDS}, and
     * {@link #DEFAULT_UPDATE_INTERVAL_METERS}.
     *
     * @return Default location request options.
     */
    @NonNull
    public static LocationRequestOptions createDefaultOptions() {
        return new LocationRequestOptions(
                DEFAULT_REQUEST_PRIORITY,
                DEFAULT_UPDATE_INTERVAL_MILLISECONDS,
                DEFAULT_UPDATE_INTERVAL_METERS);
    }

    /**
     * The priority of the request.
     *
     * @return The priority of the request.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * The minimum time in milliseconds between location updates in milliseconds.
     *
     * @return The desired interval for location updates in milliseconds.
     */
    public long getMinTime() {
        return minTime;
    }

    /**
     * The minimum distance of meters between location updates.
     *
     * @return The minimum distance of meters between location updates.
     */
    public float getMinDistance() {
        return minDistance;
    }

    @NonNull
    @Override
    public String toString() {
        return "LocationRequestOptions: Priority " + priority + " minTime " + minTime + " minDistance " + minDistance;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (!(o instanceof LocationRequestOptions)) {
            return false;
        }

        LocationRequestOptions other = (LocationRequestOptions) o;
        return other.priority == priority &&
                other.minTime == minTime &&
                other.minDistance == minDistance;
    }

    /**
     * Verifies that the minTime is valid.
     *
     * @param minTime The value to verify.
     */
    private static void verifyMinTime(long minTime) {
        if (minTime < 0) {
            throw new IllegalArgumentException("minTime must be greater or equal to 0");
        }
    }

    /**
     * Verifies that the minDistance is valid.
     *
     * @param minDistance The value to verify.
     */
    private static void verifyMinDistance(float minDistance) {
        if (minDistance < 0) {
            throw new IllegalArgumentException("minDistance must be greater or equal to 0");
        }
    }

    /**
     * Verifies the priority is valid.
     *
     * @param priority The value to verify.
     */
    private static void verifyPriority(int priority) {
        switch (priority) {
            case PRIORITY_BALANCED_POWER_ACCURACY:
            case PRIORITY_HIGH_ACCURACY:
            case PRIORITY_LOW_POWER:
            case PRIORITY_NO_POWER:
                break;
            default:
                throw new IllegalArgumentException("Priority can only be either " +
                        "PRIORITY_HIGH_ACCURACY, PRIORITY_BALANCED_POWER_ACCURACY, " +
                        "PRIORITY_LOW_POWER, or PRIORITY_NO_POWER");
        }
    }

    @NonNull
    @Override
    public JsonValue toJsonValue() {
        Map<String, Object> map = new HashMap<>();
        map.put("priority", getPriority());
        map.put("minDistance", getMinDistance());
        map.put("minTime", getMinTime());

        try {
            return JsonValue.wrap(map);
        } catch (JsonException e) {
            Logger.error(e, "LocationRequestOptions - Unable to serialize to JSON.");
            return JsonValue.NULL;
        }
    }

    /**
     * {@see #fromJson(JsonValue)}
     *
     * @param json The json value.
     * @return The parsed LocationRequestOptions.
     * @throws JsonException If the JSON is invalid.
     * @deprecated To be removed in SDK 12. Use {@link #fromJson(JsonValue)} instead.
     */
    @NonNull
    @Deprecated
    public static LocationRequestOptions parseJson(@Nullable String json) throws JsonException {
        return fromJson(JsonValue.parseString(json));
    }

    /**
     * Creates a LocationRequestOptions from a JSON string.
     *
     * @param value The JSON value.
     * @return The parsed LocationRequestOptions.
     * @throws JsonException If the JSON is invalid.
     */
    @NonNull
    public static LocationRequestOptions fromJson(@NonNull JsonValue value) throws JsonException {
        JsonMap jsonMap = value.getMap();

        if (jsonMap == null) {
            throw new JsonException("Invalid location request options: " + value);
        }

        Number minDistanceNumber = jsonMap.opt(MIN_DISTANCE_KEY).getNumber();

        float minDistance = minDistanceNumber == null ? DEFAULT_UPDATE_INTERVAL_METERS : minDistanceNumber.floatValue();
        long minTime = jsonMap.opt(MIN_TIME_KEY).getLong(DEFAULT_UPDATE_INTERVAL_MILLISECONDS);
        int priority = jsonMap.opt(PRIORITY_KEY).getInt(DEFAULT_REQUEST_PRIORITY);

        try {
            verifyPriority(priority);
            verifyMinDistance(minDistance);
            verifyMinTime(minTime);
        } catch (IllegalArgumentException e) {
            throw new JsonException("Invalid value.", e);
        }

        return new LocationRequestOptions(priority, minTime, minDistance);
    }

    /**
     * @hide
     */
    @NonNull
    public static final Parcelable.Creator<LocationRequestOptions> CREATOR = new Parcelable.Creator<LocationRequestOptions>() {
        @Override
        public LocationRequestOptions createFromParcel(@NonNull Parcel in) {
            return new LocationRequestOptions(in);
        }

        @NonNull
        @Override
        public LocationRequestOptions[] newArray(int size) {
            return new LocationRequestOptions[size];
        }
    };

    /**
     * Builder factory method.
     *
     * @return A new builder instance.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Builder to construct LocationRequestOptions.
     */
    public static class Builder {

        private long minTime = DEFAULT_UPDATE_INTERVAL_MILLISECONDS;
        private float minDistance = DEFAULT_UPDATE_INTERVAL_METERS;
        private int priority = DEFAULT_REQUEST_PRIORITY;

        /**
         * Sets the min time between location updates.
         * <p>
         * Defaults to {@link #DEFAULT_UPDATE_INTERVAL_MILLISECONDS}
         *
         * @param time The duration.
         * @param unit The unit of duration.
         * @return The builder.
         * @throws IllegalArgumentException if time is less than 0.
         */
        @NonNull
        public Builder setMinTime(long time, @NonNull TimeUnit unit) {
            verifyMinTime(unit.toMillis(time));
            minTime = unit.toMillis(time);
            return this;
        }

        /**
         * Sets the min distance between location updates.
         * <p>
         * Defaults to {@link #DEFAULT_UPDATE_INTERVAL_METERS}
         *
         * @param meters The distance in meters.
         * @return The builder.
         * @throws IllegalArgumentException if distance is less than 0.
         */
        @NonNull
        public Builder setMinDistance(float meters) {
            verifyMinDistance(meters);
            minDistance = meters;
            return this;
        }

        /**
         * Sets the priority of the location request.
         * <p>
         * Defaults to {@link #DEFAULT_REQUEST_PRIORITY}
         *
         * @param priority The priority.
         * @return The builder.
         * @throws IllegalArgumentException if priority is not PRIORITY_HIGH_ACCURACY,
         * PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, or PRIORITY_NO_POWER.
         */
        @NonNull
        public Builder setPriority(@Priority int priority) {
            verifyPriority(priority);
            this.priority = priority;
            return this;
        }

        /**
         * Creates the location request.
         *
         * @return The new location request option.
         * @deprecated To be removed in SDK 12. Use {@link #build()} instaed.
         */
        @NonNull
        @Deprecated
        public LocationRequestOptions create() {
            return build();
        }

        /**
         * Creates the location request.
         *
         * @return The new location request option.
         */
        @NonNull
        public LocationRequestOptions build() {
            return new LocationRequestOptions(this);
        }

    }

}
