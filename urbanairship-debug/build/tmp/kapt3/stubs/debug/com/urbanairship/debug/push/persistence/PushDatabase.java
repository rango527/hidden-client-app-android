package com.urbanairship.debug.push.persistence;

import java.lang.System;

/**
 * PushItem database.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@androidx.room.Database(entities = {com.urbanairship.debug.push.persistence.PushEntity.class}, version = 2, exportSchema = false)
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lcom/urbanairship/debug/push/persistence/PushDatabase;", "Landroidx/room/RoomDatabase;", "()V", "pushDao", "Lcom/urbanairship/debug/push/persistence/PushDao;", "Companion", "urbanairship-debug_debug"})
public abstract class PushDatabase extends androidx.room.RoomDatabase {
    public static final com.urbanairship.debug.push.persistence.PushDatabase.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.urbanairship.debug.push.persistence.PushDao pushDao();
    
    public PushDatabase() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/urbanairship/debug/push/persistence/PushDatabase$Companion;", "", "()V", "create", "Lcom/urbanairship/debug/push/persistence/PushDatabase;", "context", "Landroid/content/Context;", "urbanairship-debug_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.urbanairship.debug.push.persistence.PushDatabase create(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}