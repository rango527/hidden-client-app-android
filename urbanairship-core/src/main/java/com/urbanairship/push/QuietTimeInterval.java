/* Copyright Airship and Contributors */

package com.urbanairship.push;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.urbanairship.json.JsonException;
import com.urbanairship.json.JsonMap;
import com.urbanairship.json.JsonSerializable;
import com.urbanairship.json.JsonValue;

import java.util.Calendar;
import java.util.Date;

/**
 * Model object representing a quiet time interval.
 */
class QuietTimeInterval implements JsonSerializable {

    private static final String START_HOUR_KEY = "start_hour";
    private static final String START_MIN_KEY = "start_min";
    private static final String END_HOUR_KEY = "end_hour";
    private static final String END_MIN_KEY = "end_min";
    private static final int NOT_SET_VAL = -1;

    private final int startHour;
    private final int startMin;
    private final int endHour;
    private final int endMin;

    private QuietTimeInterval(Builder builder) {
        this.startHour = builder.startHour;
        this.startMin = builder.startMin;
        this.endHour = builder.endHour;
        this.endMin = builder.endMin;
    }

    /**
     * Determines whether we are currently in the middle of "Quiet Time".  Returns false if Quiet Time is disabled,
     * and evaluates whether or not the current date/time falls within the Quiet Time interval set by the user.
     *
     * @param now Reference time for determining if interval is in quiet time.
     * @return A boolean indicating whether it is currently "Quiet Time".
     */
    boolean isInQuietTime(@NonNull Calendar now) {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, startHour);
        start.set(Calendar.MINUTE, startMin);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, endHour);
        end.set(Calendar.MINUTE, endMin);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        now = (Calendar) now.clone();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        // Equal to either start or end
        if (now.compareTo(start) == 0 || now.compareTo(end) == 0) {
            return true;
        }

        // End is equal to start but now is not equal to either end or start
        if (end.compareTo(start) == 0) {
            return false;
        }

        // End is after start
        if (end.after(start)) {
            return now.after(start) && now.before(end);
        }

        // End is before start
        return now.before(end) || now.after(start);
    }

    /**
     * Returns the Quiet Time interval currently set by the user.
     *
     * @return An array of two Date instances, representing the start and end of Quiet Time.
     */
    @Nullable
    Date[] getQuietTimeIntervalDateArray() {
        if (startHour == NOT_SET_VAL || startMin == NOT_SET_VAL ||
                endHour == NOT_SET_VAL || endMin == NOT_SET_VAL) {
            return null;
        }

        // Grab the start date.
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, startHour);
        start.set(Calendar.MINUTE, startMin);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        // Prepare the end date.
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, endHour);
        end.set(Calendar.MINUTE, endMin);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        Date startDate = start.getTime();
        Date endDate = end.getTime();
        return new Date[] { startDate, endDate };
    }

    @NonNull
    @Override
    public JsonValue toJsonValue() {
        return JsonMap.newBuilder()
                      .put(START_HOUR_KEY, startHour)
                      .put(START_MIN_KEY, startMin)
                      .put(END_HOUR_KEY, endHour)
                      .put(END_MIN_KEY, endMin)
                      .build()
                      .toJsonValue();
    }

    /**
     * Static helper method to deserialize JSON into a QuietTimeInterval instance.
     *
     * @param value The JSON value.
     * @return The deserialized QuietTimeInterval instance.
     */
    public static QuietTimeInterval fromJson(@NonNull JsonValue value) throws JsonException {
        JsonMap jsonMap = value.optMap();
        if (jsonMap.isEmpty()) {
            throw new JsonException("Invalid quiet time interval: " + value);
        }

        return new Builder()
                .setStartHour(jsonMap.opt(START_HOUR_KEY).getInt(NOT_SET_VAL))
                .setStartMin(jsonMap.opt(START_MIN_KEY).getInt(NOT_SET_VAL))
                .setEndHour(jsonMap.opt(END_HOUR_KEY).getInt(NOT_SET_VAL))
                .setEndMin(jsonMap.opt(END_MIN_KEY).getInt(NOT_SET_VAL))
                .build();
    }

    @NonNull
    @Override
    public String toString() {
        return "QuietTimeInterval{" +
                "startHour=" + startHour +
                ", startMin=" + startMin +
                ", endHour=" + endHour +
                ", endMin=" + endMin +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuietTimeInterval that = (QuietTimeInterval) o;

        if (startHour != that.startHour) {
            return false;
        }
        if (startMin != that.startMin) {
            return false;
        }
        if (endHour != that.endHour) {
            return false;
        }
        return endMin == that.endMin;

    }

    @Override
    public int hashCode() {
        int result = startHour;
        result = 31 * result + startMin;
        result = 31 * result + endHour;
        result = 31 * result + endMin;
        return result;
    }

    /**
     * Builder factory method.
     *
     * @return A new builder instance.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * QuietTimeInterval builder class.
     */
    public static class Builder {

        private int startHour = -1;
        private int startMin = -1;
        private int endHour = -1;
        private int endMin = -1;

        /**
         * Set the quiet time interval.
         *
         * @param startTime The interval start time as a Date.
         * @param endTime The interval end time as a Date.
         * @return The builder with the interval set.
         */
        @NonNull
        public Builder setQuietTimeInterval(@NonNull Date startTime, @NonNull Date endTime) {
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startTime);
            this.startHour = startCal.get(Calendar.HOUR_OF_DAY);
            this.startMin = startCal.get(Calendar.MINUTE);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endTime);
            this.endHour = endCal.get(Calendar.HOUR_OF_DAY);
            this.endMin = endCal.get(Calendar.MINUTE);
            return this;
        }

        /**
         * Set the quiet time interval start hour. Value should be between 0 and 23.
         *
         * @param startHour The start hour as an int.
         * @return The builder with the start hour set.
         */
        @NonNull
        public Builder setStartHour(@IntRange(from = 0, to = 23) int startHour) {
            this.startHour = startHour;
            return this;
        }

        /**
         * Set the quiet time interval start min. Value should be between 0 and 59.
         *
         * @param startMin The start min as an int.
         * @return The builder with the start min set.
         */
        @NonNull
        public Builder setStartMin(@IntRange(from = 0, to = 59) int startMin) {
            this.startMin = startMin;
            return this;
        }

        /**
         * Set the quiet time interval end hour.  Value should be between 0 and 23.
         *
         * @param endHour The end hour as an int.
         * @return The builder with the end hour set.
         */
        @NonNull
        public Builder setEndHour(@IntRange(from = 0, to = 23) int endHour) {
            this.endHour = endHour;
            return this;
        }

        /**
         * Set the quiet time interval end min. Value should be between 0 and 59.
         *
         * @param endMin The end min as an int.
         * @return The builder with the end min set.
         */
        @NonNull
        public Builder setEndMin(@IntRange(from = 0, to = 59) int endMin) {
            this.endMin = endMin;
            return this;
        }

        /**
         * Build the QuietTimeInterval instance.
         *
         * @return The QuietTimeInterval instance.
         */
        @NonNull
        public QuietTimeInterval build() {
            return new QuietTimeInterval(this);
        }

    }

}
