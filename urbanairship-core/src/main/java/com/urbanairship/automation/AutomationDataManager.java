/* Copyright Airship and Contributors */

package com.urbanairship.automation;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.urbanairship.Logger;
import com.urbanairship.util.DataManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.urbanairship.util.UAStringUtil.repeat;

/**
 * {@link DataManager} class for automation schedules.
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class AutomationDataManager extends DataManager {

    /**
     * Maximum SQL argument count.
     */
    private static final int MAX_ARG_COUNT = 999;

    /**
     * The database version
     */
    private static final int DATABASE_VERSION = 5;

    /**
     * Appended to the end of schedules GET queries to group rows by schedule ID.
     */
    private static final String ORDER_SCHEDULES_STATEMENT = " ORDER BY " + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + " ASC";

    /**
     * Query for retrieving schedules with associated delays.
     */
    private static final String GET_SCHEDULES_QUERY = "SELECT * FROM " + ScheduleEntry.TABLE_NAME + " a"
            + " LEFT OUTER JOIN " + TriggerEntry.TABLE_NAME + " b ON a." + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + "=b." + TriggerEntry.COLUMN_NAME_SCHEDULE_ID;

    /**
     * Query for retrieving active triggers.
     */
    private static final String GET_ACTIVE_TRIGGERS = "SELECT * FROM " + TriggerEntry.TABLE_NAME + " t" +
            " LEFT OUTER JOIN " + ScheduleEntry.TABLE_NAME + " a ON a." + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + " = t." + TriggerEntry.COLUMN_NAME_SCHEDULE_ID +
            " WHERE t." + TriggerEntry.COLUMN_NAME_TYPE + " = ? AND a." + ScheduleEntry.COLUMN_NAME_START + " < ?" +
            " AND ((t." + TriggerEntry.COLUMN_NAME_IS_CANCELLATION + " = 1 AND a." + ScheduleEntry.COLUMN_NAME_EXECUTION_STATE + " IN (" + ScheduleEntry.STATE_WAITING_SCHEDULE_CONDITIONS + "," + ScheduleEntry.STATE_TIME_DELAYED + "," + ScheduleEntry.STATE_PREPARING_SCHEDULE + "))" +
            " OR (t." + TriggerEntry.COLUMN_NAME_IS_CANCELLATION + " = 0 AND a." + ScheduleEntry.COLUMN_NAME_EXECUTION_STATE + " = " + ScheduleEntry.STATE_IDLE + "))" +
            " AND t." + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + " LIKE ?";

    /**
     * Class constructor.
     *
     * @param context The app context.
     * @param appKey The app key.
     */
    public AutomationDataManager(@NonNull Context context, @NonNull String appKey, @NonNull String dbName) {
        super(context, appKey, dbName, DATABASE_VERSION);
    }

    @Override
    protected void onCreate(@NonNull SQLiteDatabase db) {
        Logger.debug("AutomationDataManager - Creating automation database");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + ScheduleEntry.TABLE_NAME + " ("
                + ScheduleEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + " TEXT UNIQUE,"
                + ScheduleEntry.COLUMN_NAME_METADATA + " TEXT,"
                + ScheduleEntry.COLUMN_NAME_DATA + " TEXT,"
                + ScheduleEntry.COLUMN_NAME_START + " INTEGER,"
                + ScheduleEntry.COLUMN_NAME_END + " INTEGER,"
                + ScheduleEntry.COLUMN_EDIT_GRACE_PERIOD + " INTEGER,"
                + ScheduleEntry.COLUMN_NAME_EXECUTION_STATE_CHANGE_DATE + " INTEGER,"
                + ScheduleEntry.COLUMN_NAME_COUNT + " INTEGER,"
                + ScheduleEntry.COLUMN_NAME_LIMIT + " INTEGER,"
                + ScheduleEntry.COLUMN_NAME_PRIORITY + " INTEGER,"
                + ScheduleEntry.COLUMN_NAME_GROUP + " TEXT,"
                + ScheduleEntry.COLUMN_NAME_EXECUTION_STATE + " INTEGER,"
                + ScheduleEntry.COLUMN_NAME_DELAY_FINISH_DATE + " DOUBLE,"
                + ScheduleEntry.COLUMN_NAME_APP_STATE + " INTEGER,"
                + ScheduleEntry.COLUMN_NAME_REGION_ID + " TEXT,"
                + ScheduleEntry.COLUMN_NAME_SCREEN + " TEXT,"
                + ScheduleEntry.COLUMN_NAME_SECONDS + " DOUBLE,"
                + ScheduleEntry.COLUMN_NAME_INTERVAL + " INTEGER"

                + ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TriggerEntry.TABLE_NAME + " ("
                + TriggerEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TriggerEntry.COLUMN_NAME_TYPE + " INTEGER,"
                + TriggerEntry.COLUMN_NAME_IS_CANCELLATION + " INTEGER,"
                + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + " TEXT,"
                + TriggerEntry.COLUMN_NAME_PREDICATE + " TEXT,"
                + TriggerEntry.COLUMN_NAME_PROGRESS + " DOUBLE,"
                + TriggerEntry.COLUMN_NAME_GOAL + " DOUBLE,"
                + "FOREIGN KEY(" + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + ") REFERENCES " + ScheduleEntry.TABLE_NAME + "(" + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + ") ON DELETE CASCADE"
                + ");");

        Logger.debug("AutomationDataManager - Automation database created");
    }

    @Override
    protected void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade 1 -> 2 changes (SDK 8.4)
        //
        //      action_schedules:
        //          * _id column renamed to s_row_id
        //          * schedule delay information added
        //
        //      triggers
        //          * _id column renamed to t_row_id
        //          * is_cancellation column added
        //          * t_start column removed
        //
        // Upgrade 2 -> 3 changes (SDK 9.0 alpha)
        //
        //      action_schedules:
        //          * s_is_pending_execution column renamed to s_execution_state
        //          * s_actions column renamed to s_data
        //          * added s_priority
        //
        // Upgrade 3 -> 4 changes (SDK 9.0 beta)
        //
        //      action_schedules:
        //          * added s_execution_state_change_date, s_edit_grace_period, s_interval

        String tempScheduleTableName = "temp_schedule_entry_table";
        String tempTriggersTableName = "temp_triggers_entry_table";
        String oldIdColumn = "_id";
        String oldActionsColumn = "s_actions";
        String oldPendingExecutionColumn = "s_is_pending_execution";

        switch (oldVersion) {
            case 1:
                // Update the schedule table and rename the ID column.

                db.execSQL("BEGIN TRANSACTION;");
                db.execSQL("ALTER TABLE " + ScheduleEntry.TABLE_NAME + " RENAME TO " + tempScheduleTableName + ";");
                db.execSQL("ALTER TABLE " + TriggerEntry.TABLE_NAME + " RENAME TO " + tempTriggersTableName + ";");

                db.execSQL("CREATE TABLE " + ScheduleEntry.TABLE_NAME + " ("
                        + ScheduleEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + " TEXT UNIQUE,"
                        + oldActionsColumn + " TEXT,"
                        + ScheduleEntry.COLUMN_NAME_START + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_END + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_COUNT + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_LIMIT + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_GROUP + " TEXT,"
                        + oldPendingExecutionColumn + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_DELAY_FINISH_DATE + " DOUBLE,"
                        + ScheduleEntry.COLUMN_NAME_APP_STATE + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_REGION_ID + " TEXT,"
                        + ScheduleEntry.COLUMN_NAME_SCREEN + " TEXT,"
                        + ScheduleEntry.COLUMN_NAME_SECONDS + " DOUBLE"
                        + ");");

                db.execSQL("CREATE TABLE " + TriggerEntry.TABLE_NAME + "("
                        + TriggerEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + TriggerEntry.COLUMN_NAME_TYPE + " INTEGER,"
                        + TriggerEntry.COLUMN_NAME_IS_CANCELLATION + " INTEGER,"
                        + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + " TEXT,"
                        + TriggerEntry.COLUMN_NAME_PREDICATE + " TEXT,"
                        + TriggerEntry.COLUMN_NAME_PROGRESS + " DOUBLE,"
                        + TriggerEntry.COLUMN_NAME_GOAL + " DOUBLE,"
                        + "FOREIGN KEY(" + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + ") REFERENCES " + ScheduleEntry.TABLE_NAME + "(" + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + ") ON DELETE CASCADE"
                        + ");");

                db.execSQL("INSERT INTO " + ScheduleEntry.TABLE_NAME + "("
                        + ScheduleEntry.COLUMN_NAME_ID + ", "
                        + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + ", "
                        + oldActionsColumn + ", "
                        + ScheduleEntry.COLUMN_NAME_START + ", "
                        + ScheduleEntry.COLUMN_NAME_END + ", "
                        + ScheduleEntry.COLUMN_NAME_COUNT + ", "
                        + ScheduleEntry.COLUMN_NAME_LIMIT + ", "
                        + ScheduleEntry.COLUMN_NAME_PRIORITY + ", "
                        + ScheduleEntry.COLUMN_NAME_GROUP + ", "
                        + oldPendingExecutionColumn + ", "
                        + ScheduleEntry.COLUMN_NAME_DELAY_FINISH_DATE + ", "
                        + ScheduleEntry.COLUMN_NAME_APP_STATE + ", "
                        + ScheduleEntry.COLUMN_NAME_REGION_ID + ", "
                        + ScheduleEntry.COLUMN_NAME_SCREEN + ", "
                        + ScheduleEntry.COLUMN_NAME_SECONDS + ") " +
                        "SELECT "
                        + oldIdColumn + ", "
                        + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + ", "
                        + oldActionsColumn + ", "
                        + ScheduleEntry.COLUMN_NAME_START + ", "
                        + ScheduleEntry.COLUMN_NAME_END + ", "
                        + ScheduleEntry.COLUMN_NAME_COUNT + ", "
                        + ScheduleEntry.COLUMN_NAME_LIMIT + ", "
                        + ScheduleEntry.COLUMN_NAME_GROUP + ", "
                        + "0, 0.0, 1, NULL, NULL, 0 " +
                        "FROM " + tempScheduleTableName + ";");

                db.execSQL("INSERT INTO " + TriggerEntry.TABLE_NAME + "("
                        + TriggerEntry.COLUMN_NAME_ID + ", "
                        + TriggerEntry.COLUMN_NAME_TYPE + ", "
                        + TriggerEntry.COLUMN_NAME_IS_CANCELLATION + ", "
                        + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + ", "
                        + TriggerEntry.COLUMN_NAME_PREDICATE + ", "
                        + TriggerEntry.COLUMN_NAME_PROGRESS + ", "
                        + TriggerEntry.COLUMN_NAME_GOAL + ") " +
                        "SELECT "
                        + oldIdColumn + ", "
                        + TriggerEntry.COLUMN_NAME_TYPE + ", "
                        + "0, "
                        + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + ", "
                        + TriggerEntry.COLUMN_NAME_PREDICATE + ", "
                        + TriggerEntry.COLUMN_NAME_PROGRESS + ", "
                        + TriggerEntry.COLUMN_NAME_GOAL +
                        " FROM " + tempTriggersTableName + ";");

                db.execSQL("DROP TABLE " + tempScheduleTableName + ";");
                db.execSQL("DROP TABLE " + tempTriggersTableName + ";");
                db.execSQL("COMMIT;");

            case 2:
                db.execSQL("BEGIN TRANSACTION;");
                db.execSQL("ALTER TABLE " + ScheduleEntry.TABLE_NAME + " RENAME TO " + tempScheduleTableName + ";");
                db.execSQL("ALTER TABLE " + TriggerEntry.TABLE_NAME + " RENAME TO " + tempTriggersTableName + ";");

                db.execSQL("CREATE TABLE " + ScheduleEntry.TABLE_NAME + " ("
                        + ScheduleEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + " TEXT UNIQUE,"
                        + ScheduleEntry.COLUMN_NAME_DATA + " TEXT,"
                        + ScheduleEntry.COLUMN_NAME_START + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_END + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_COUNT + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_LIMIT + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_PRIORITY + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_GROUP + " TEXT,"
                        + ScheduleEntry.COLUMN_NAME_EXECUTION_STATE + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_DELAY_FINISH_DATE + " DOUBLE,"
                        + ScheduleEntry.COLUMN_NAME_APP_STATE + " INTEGER,"
                        + ScheduleEntry.COLUMN_NAME_REGION_ID + " TEXT,"
                        + ScheduleEntry.COLUMN_NAME_SCREEN + " TEXT,"
                        + ScheduleEntry.COLUMN_NAME_SECONDS + " DOUBLE"
                        + ");");

                db.execSQL("CREATE TABLE IF NOT EXISTS " + TriggerEntry.TABLE_NAME + " ("
                        + TriggerEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + TriggerEntry.COLUMN_NAME_TYPE + " INTEGER,"
                        + TriggerEntry.COLUMN_NAME_IS_CANCELLATION + " INTEGER,"
                        + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + " TEXT,"
                        + TriggerEntry.COLUMN_NAME_PREDICATE + " TEXT,"
                        + TriggerEntry.COLUMN_NAME_PROGRESS + " DOUBLE,"
                        + TriggerEntry.COLUMN_NAME_GOAL + " DOUBLE,"
                        + "FOREIGN KEY(" + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + ") REFERENCES " + ScheduleEntry.TABLE_NAME + "(" + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + ") ON DELETE CASCADE"
                        + ");");

                db.execSQL("INSERT INTO " + ScheduleEntry.TABLE_NAME + "("
                        + ScheduleEntry.COLUMN_NAME_ID + ", "
                        + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + ", "
                        + ScheduleEntry.COLUMN_NAME_DATA + ", "
                        + ScheduleEntry.COLUMN_NAME_START + ", "
                        + ScheduleEntry.COLUMN_NAME_END + ", "
                        + ScheduleEntry.COLUMN_NAME_COUNT + ", "
                        + ScheduleEntry.COLUMN_NAME_LIMIT + ", "
                        + ScheduleEntry.COLUMN_NAME_PRIORITY + ", "
                        + ScheduleEntry.COLUMN_NAME_GROUP + ", "
                        + ScheduleEntry.COLUMN_NAME_EXECUTION_STATE + ", "
                        + ScheduleEntry.COLUMN_NAME_DELAY_FINISH_DATE + ", "
                        + ScheduleEntry.COLUMN_NAME_APP_STATE + ", "
                        + ScheduleEntry.COLUMN_NAME_REGION_ID + ", "
                        + ScheduleEntry.COLUMN_NAME_SCREEN + ", "
                        + ScheduleEntry.COLUMN_NAME_SECONDS + ") " +
                        "SELECT "
                        + ScheduleEntry.COLUMN_NAME_ID + ", "
                        + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + ", "
                        + oldActionsColumn + ", "
                        + ScheduleEntry.COLUMN_NAME_START + ", "
                        + ScheduleEntry.COLUMN_NAME_END + ", "
                        + ScheduleEntry.COLUMN_NAME_COUNT + ", "
                        + ScheduleEntry.COLUMN_NAME_LIMIT + ", "
                        + "0, " // Default priority
                        + ScheduleEntry.COLUMN_NAME_GROUP + ", "
                        + oldPendingExecutionColumn + ", "
                        + ScheduleEntry.COLUMN_NAME_DELAY_FINISH_DATE + ", "
                        + ScheduleEntry.COLUMN_NAME_APP_STATE + ", "
                        + ScheduleEntry.COLUMN_NAME_REGION_ID + ", "
                        + ScheduleEntry.COLUMN_NAME_SCREEN + ", "
                        + ScheduleEntry.COLUMN_NAME_SECONDS + " " +
                        "FROM " + tempScheduleTableName + ";");

                db.execSQL("INSERT INTO " + TriggerEntry.TABLE_NAME + "("
                        + TriggerEntry.COLUMN_NAME_ID + ", "
                        + TriggerEntry.COLUMN_NAME_TYPE + ", "
                        + TriggerEntry.COLUMN_NAME_IS_CANCELLATION + ", "
                        + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + ", "
                        + TriggerEntry.COLUMN_NAME_PREDICATE + ", "
                        + TriggerEntry.COLUMN_NAME_PROGRESS + ", "
                        + TriggerEntry.COLUMN_NAME_GOAL + ") " +
                        "SELECT "
                        + TriggerEntry.COLUMN_NAME_ID + ", "
                        + TriggerEntry.COLUMN_NAME_TYPE + ", "
                        + TriggerEntry.COLUMN_NAME_IS_CANCELLATION + ", "
                        + TriggerEntry.COLUMN_NAME_SCHEDULE_ID + ", "
                        + TriggerEntry.COLUMN_NAME_PREDICATE + ", "
                        + TriggerEntry.COLUMN_NAME_PROGRESS + ", "
                        + TriggerEntry.COLUMN_NAME_GOAL +
                        " FROM " + tempTriggersTableName + ";");

                db.execSQL("DROP TABLE " + tempScheduleTableName + ";");
                db.execSQL("DROP TABLE " + tempTriggersTableName + ";");

                db.execSQL("COMMIT;");

            case 3:
                db.execSQL("BEGIN TRANSACTION;");
                db.execSQL("ALTER TABLE " + ScheduleEntry.TABLE_NAME + " ADD COLUMN " + ScheduleEntry.COLUMN_NAME_EXECUTION_STATE_CHANGE_DATE + " INTEGER;");
                db.execSQL("ALTER TABLE " + ScheduleEntry.TABLE_NAME + " ADD COLUMN " + ScheduleEntry.COLUMN_EDIT_GRACE_PERIOD + " INTEGER;");
                db.execSQL("ALTER TABLE " + ScheduleEntry.TABLE_NAME + " ADD COLUMN " + ScheduleEntry.COLUMN_NAME_INTERVAL + " INTEGER;");
                db.execSQL("COMMIT;");

            case 4:
                db.execSQL("BEGIN TRANSACTION;");
                db.execSQL("ALTER TABLE " + ScheduleEntry.TABLE_NAME + " ADD COLUMN " + ScheduleEntry.COLUMN_NAME_METADATA + " TEXT;");
                db.execSQL("COMMIT;");

                break;
            default:
                // Kills the table and existing data
                db.execSQL("DROP TABLE IF EXISTS " + ScheduleEntry.TABLE_NAME);
                db.execSQL("DROP TABLE IF EXISTS " + TriggerEntry.TABLE_NAME);

                // Recreates the database with a new version
                onCreate(db);
        }
    }

    @Override
    protected void onDowngrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // Logs that the database is being downgraded
        Logger.debug("AutomationDataManager - Dropping automation database. Downgrading from version %s to %s", oldVersion, newVersion);

        // Drop the table and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + TriggerEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScheduleEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void onConfigure(@NonNull SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    /**
     * Deletes a schedule given an ID.
     *
     * @param scheduleId The schedule ID.
     */
    void deleteSchedule(String scheduleId) {
        if (delete(ScheduleEntry.TABLE_NAME, ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + " = ?", new String[] { scheduleId }) < 0) {
            Logger.error("AutomationDataManager - failed to delete schedule for schedule ID %s", scheduleId);
        }
    }

    /**
     * Deletes schedules given a group.
     *
     * @param group The schedule group.
     * @return {@code true} if the group was deleted, otherwise {@code false}.
     */
    boolean deleteGroup(String group) {
        if (delete(ScheduleEntry.TABLE_NAME, ScheduleEntry.COLUMN_NAME_GROUP + " = ?", new String[] { group }) < 0) {
            Logger.error("AutomationDataManager - failed to delete schedules for group %s", group);
            return false;
        }
        return true;
    }

    /**
     * Deletes all schedules.
     */
    void deleteAllSchedules() {
        if (delete(ScheduleEntry.TABLE_NAME, null, null) < 0) {
            Logger.error("AutomationDataManager - failed to delete schedules");
        }
    }

    /**
     * Saves schedules.
     *
     * @param scheduleEntries Collection of schedule entries.
     */
    void saveSchedules(@NonNull Collection<ScheduleEntry> scheduleEntries) {
        if (scheduleEntries.isEmpty()) {
            return;
        }

        final SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            Logger.error("AutomationDataManager - Unable to update automation rules.");
            return;
        }

        try {
            db.beginTransaction();

            for (ScheduleEntry scheduleEntry : scheduleEntries) {
                if (!scheduleEntry.save(db)) {
                    db.endTransaction();
                    return;
                }
            }

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (SQLException e) {
            Logger.error(e, "AutomationDataManager - Unable to save schedules.");
        }
    }

    /**
     * Saves a single schedule entry.
     *
     * @param entry The entry to save.
     */
    void saveSchedule(@NonNull ScheduleEntry entry) {
        final SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            Logger.error("AutomationDataManager - Unable to update automation rules.");
            return;
        }

        try {
            db.beginTransaction();
            if (entry.save(db)) {
                db.setTransactionSuccessful();
            }
            db.endTransaction();
        } catch (SQLException e) {
            Logger.error(e, "AutomationDataManager - Unable to save schedule.");
        }
    }

    /**
     * Saves triggers.
     *
     * @param triggerEntries Collection of trigger entries.
     */
    void saveTriggers(@NonNull Collection<TriggerEntry> triggerEntries) {
        if (triggerEntries.isEmpty()) {
            return;
        }

        final SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            Logger.error("AutomationDataManager - Unable to update automation rules.");
            return;
        }

        try {
            db.beginTransactionNonExclusive();

            for (TriggerEntry triggerEntry : triggerEntries) {
                if (!triggerEntry.save(db)) {
                    db.endTransaction();
                    return;
                }
            }

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (SQLException e) {
            Logger.error(e, "AutomationDataManager - Unable to save triggers.");
        }
    }

    /**
     * Deletes schedules given a list of groups.
     *
     * @param groups The list of groups.
     */
    void deleteGroups(@NonNull Collection<String> groups) {
        if (groups.isEmpty()) {
            return;
        }

        performSubSetOperations(groups, new SetOperation<String>() {
            @Override
            public void perform(@NonNull List<String> subset) {
                String inStatement = repeat("?", subset.size(), ", ");
                delete(ScheduleEntry.TABLE_NAME, ScheduleEntry.COLUMN_NAME_GROUP + " IN ( " + inStatement + " )", subset.toArray(new String[0]));
            }
        });
    }

    /**
     * Deletes schedules given a list of IDs.
     *
     * @param schedulesToDelete The list of schedule IDs.
     */
    void deleteSchedules(@NonNull Collection<String> schedulesToDelete) {
        if (schedulesToDelete.isEmpty()) {
            return;
        }

        performSubSetOperations(schedulesToDelete, new SetOperation<String>() {
            @Override
            public void perform(@NonNull List<String> subset) {
                String inStatement = repeat("?", subset.size(), ", ");
                delete(ScheduleEntry.TABLE_NAME, ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + " IN ( " + inStatement + " )", subset.toArray(new String[0]));
            }
        });
    }

    /**
     * Gets schedules for a given group.
     *
     * @param group The schedule group.
     * @return The list of {@link ScheduleEntry} instances.
     */
    @NonNull
    List<ScheduleEntry> getScheduleEntries(@NonNull String group) {
        String query = GET_SCHEDULES_QUERY + " WHERE a." + ScheduleEntry.COLUMN_NAME_GROUP + "=?" + ORDER_SCHEDULES_STATEMENT;
        Cursor cursor = rawQuery(query, new String[] { String.valueOf(group) });
        if (cursor == null) {
            return Collections.emptyList();
        }

        List<ScheduleEntry> entries = generateSchedules(cursor);
        cursor.close();
        return entries;
    }

    /**
     * Gets all schedules.
     *
     * @return The list of {@link ScheduleEntry} instances.
     */
    @NonNull
    List<ScheduleEntry> getScheduleEntries() {
        String query = GET_SCHEDULES_QUERY + ORDER_SCHEDULES_STATEMENT;
        Cursor cursor = rawQuery(query, null);
        if (cursor == null) {
            return Collections.emptyList();
        }

        List<ScheduleEntry> entries = generateSchedules(cursor);
        cursor.close();
        return entries;
    }

    /**
     * Gets a schedule entry for the given ID.
     *
     * @param scheduleId The schedule ID.
     * @return A schedule entry or null if the schedule is unavailable.
     */
    @Nullable
    ScheduleEntry getScheduleEntry(@NonNull String scheduleId) {
        Set<String> hashSet = new HashSet<>();
        hashSet.add(scheduleId);
        List<ScheduleEntry> scheduleEntries = getScheduleEntries(hashSet);
        return scheduleEntries.size() > 0 ? scheduleEntries.get(0) : null;
    }

    /**
     * Gets schedules for a given set of IDs.
     *
     * @param ids The set of schedule IDs.
     * @return The list of {@link ScheduleEntry} instances.
     */
    @NonNull
    List<ScheduleEntry> getScheduleEntries(@NonNull Set<String> ids) {
        final List<ScheduleEntry> schedules = new ArrayList<>(ids.size());

        performSubSetOperations(ids, new SetOperation<String>() {
            @Override
            public void perform(@NonNull List<String> subset) {
                String query = GET_SCHEDULES_QUERY + " WHERE a." + ScheduleEntry.COLUMN_NAME_SCHEDULE_ID + " IN ( " + repeat("?", subset.size(), ", ") + ")" + ORDER_SCHEDULES_STATEMENT;

                Cursor cursor = rawQuery(query, subset.toArray(new String[0]));
                if (cursor != null) {
                    schedules.addAll(generateSchedules(cursor));
                    cursor.close();
                }

            }
        });

        return schedules;
    }

    /**
     * Gets schedules for a given state.
     *
     * @param executionState The execution state.
     * @return A list of schedules.
     */
    @NonNull
    List<ScheduleEntry> getScheduleEntries(@ScheduleEntry.State int executionState) {
        String query = GET_SCHEDULES_QUERY + " WHERE a." + ScheduleEntry.COLUMN_NAME_EXECUTION_STATE + " = ?";
        Cursor cursor = rawQuery(query, new String[] { String.valueOf(executionState) });

        if (cursor == null) {
            return Collections.emptyList();
        }

        List<ScheduleEntry> entries = generateSchedules(cursor);
        cursor.close();
        return entries;
    }

    /**
     * Gets schedules for a given state.
     *
     * @param executionStates The execution state.
     * @return A list of schedules.
     */
    @NonNull
    List<ScheduleEntry> getScheduleEntries(@NonNull @ScheduleEntry.State int... executionStates) {
        String[] states = new String[executionStates.length];
        for (int i = 0; i < executionStates.length; i++) {
            states[i] = String.valueOf(executionStates[i]);
        }

        String query = GET_SCHEDULES_QUERY + " WHERE a." + ScheduleEntry.COLUMN_NAME_EXECUTION_STATE + " IN ( " + repeat("?", executionStates.length, ", ") + ")";
        Cursor cursor = rawQuery(query, states);

        if (cursor == null) {
            return Collections.emptyList();
        }

        List<ScheduleEntry> entries = generateSchedules(cursor);
        cursor.close();
        return entries;
    }

    /**
     * Gets the active schedule entries that are expired.
     *
     * @return The list of expired schedules.
     */
    @NonNull
    List<ScheduleEntry> getActiveExpiredScheduleEntries() {
        String query = GET_SCHEDULES_QUERY +
                " WHERE a." + ScheduleEntry.COLUMN_NAME_EXECUTION_STATE + " != " + ScheduleEntry.STATE_FINISHED +
                " AND a." + ScheduleEntry.COLUMN_NAME_END + " >= 0 AND a." + ScheduleEntry.COLUMN_NAME_END + " <= ?";

        Cursor cursor = rawQuery(query, new String[] { String.valueOf(System.currentTimeMillis()) });

        if (cursor == null) {
            return Collections.emptyList();
        }

        List<ScheduleEntry> entries = generateSchedules(cursor);
        cursor.close();
        return entries;
    }

    /**
     * Gets triggers for a given type.
     *
     * @param type The trigger type.
     * @return THe list of {@link TriggerEntry} instances.
     */
    @NonNull
    List<TriggerEntry> getActiveTriggerEntries(int type) {
        return getActiveTriggerEntries(type, "%");
    }

    /**
     * Gets triggers for a given type.
     *
     * @param type The trigger type.
     * @param scheduleId The ID of the schedule containing the trigger
     * @return THe list of {@link TriggerEntry} instances.
     */
    @NonNull
    List<TriggerEntry> getActiveTriggerEntries(int type, @NonNull String scheduleId) {
        List<TriggerEntry> triggers = new ArrayList<>();
        Cursor cursor = rawQuery(GET_ACTIVE_TRIGGERS, new String[] { String.valueOf(type), String.valueOf(System.currentTimeMillis()), scheduleId });

        if (cursor == null) {
            return triggers;
        }

        // create triggers
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            TriggerEntry triggerEntry = new TriggerEntry(cursor);
            triggers.add(triggerEntry);
            cursor.moveToNext();
        }

        cursor.close();
        return triggers;
    }

    /**
     * Returns the current schedule count.
     *
     * @return The current schedule count.
     */
    public long getScheduleCount() {
        final SQLiteDatabase db = getReadableDatabase();
        if (db == null) {
            return -1;
        }

        return DatabaseUtils.queryNumEntries(db, ScheduleEntry.TABLE_NAME);
    }

    /**
     * Helper method to generate schedule entries from a a cursor.
     *
     * @param cursor The cursor.
     * @return A list of schedule entries.
     */
    @NonNull
    private List<ScheduleEntry> generateSchedules(@NonNull Cursor cursor) {
        cursor.moveToFirst();

        List<ScheduleEntry> entries = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            ScheduleEntry entry = ScheduleEntry.fromCursor(cursor);
            if (entry != null) {
                entries.add(entry);
            }
            cursor.moveToNext();
        }

        return entries;
    }

    /**
     * Interface for operating on a subset of IDs.
     *
     * @param <T> The list element type.
     */
    interface SetOperation<T> {

        void perform(@NonNull List<T> subset);

    }

    /**
     * Performs an operation on a list of elements.
     *
     * @param <T> The list element type.
     * @param ids The list of IDs.
     * @param operation The operation to perform.
     */
    private static <T> void performSubSetOperations(@NonNull Collection<T> ids, @NonNull SetOperation<T> operation) {
        List<T> remaining = new ArrayList<>(ids);

        while (!remaining.isEmpty()) {
            if (remaining.size() > AutomationDataManager.MAX_ARG_COUNT) {
                operation.perform(remaining.subList(0, AutomationDataManager.MAX_ARG_COUNT));
                remaining = remaining.subList(AutomationDataManager.MAX_ARG_COUNT, remaining.size());
            } else {
                operation.perform(remaining);
                remaining.clear();
            }
        }
    }

}
