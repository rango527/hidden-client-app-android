/* Copyright Airship and Contributors */

package com.urbanairship.analytics.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.urbanairship.Logger;
import com.urbanairship.util.DataManager;

/**
 * Database storage for events.
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class EventsStorage extends DataManager {

    /**
     * The database that the provider uses as its underlying data store
     */
    private static final String DATABASE_NAME = "ua_analytics.db";

    /**
     * The database version
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Events table contract
     */
    public interface Events extends BaseColumns {

        /**
         * The table name offered by this provider
         */
        @NonNull
        String TABLE_NAME = "events";


        /*
         * Column definitions
         */

        @NonNull
        String COLUMN_NAME_TYPE = "type";

        @NonNull
        String COLUMN_NAME_EVENT_ID = "event_id";

        @NonNull
        String COLUMN_NAME_TIME = "time";

        //serialized
        @NonNull
        String COLUMN_NAME_DATA = "data";

        @NonNull
        String COLUMN_NAME_SESSION_ID = "session_id";

        @NonNull
        String COLUMN_NAME_EVENT_SIZE = "event_size";

    }

    public EventsStorage(@NonNull Context context, @NonNull String appKey) {
        super(context, appKey, DATABASE_NAME, DATABASE_VERSION);
    }

    @Override
    protected void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // Logs that the database is being upgraded
        Logger.debug("EventsStorage - Upgrading analytics database from version %s to %s, which will destroy all old data", oldVersion, newVersion);

        // Kills the table and existing data
        db.execSQL("DROP TABLE IF EXISTS " + Events.TABLE_NAME);

        // Recreates the database with a new version
        onCreate(db);
    }

    @Override
    protected void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Events.TABLE_NAME + " ("
                + Events._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Events.COLUMN_NAME_TYPE + " TEXT,"
                + Events.COLUMN_NAME_EVENT_ID + " TEXT,"
                + Events.COLUMN_NAME_TIME + " INTEGER,"
                + Events.COLUMN_NAME_DATA + " TEXT,"
                + Events.COLUMN_NAME_SESSION_ID + " TEXT,"
                + Events.COLUMN_NAME_EVENT_SIZE + " INTEGER"
                + ");");
    }

    @Override
    protected void onDowngrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        Logger.debug("EventsStorage - Downgrading analytics database from version %s to %s, which will destroy all data.", oldVersion, newVersion);

        // Drop the table and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + Events.TABLE_NAME);
        onCreate(db);
    }

}
