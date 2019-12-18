package com.urbanairship.debug;

import java.lang.System;

/**
 * Debug manager. Initialized by UAirship instance during takeOff.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0014\u00a8\u0006\f"}, d2 = {"Lcom/urbanairship/debug/DebugManager;", "Lcom/urbanairship/AirshipComponent;", "context", "Landroid/content/Context;", "preferenceDataStore", "Lcom/urbanairship/PreferenceDataStore;", "(Landroid/content/Context;Lcom/urbanairship/PreferenceDataStore;)V", "onAirshipReady", "", "airship", "Lcom/urbanairship/UAirship;", "Companion", "urbanairship-debug_debug"})
public final class DebugManager extends com.urbanairship.AirshipComponent {
    public static final long TRIM_PUSHES_COUNT = 50L;
    public static final com.urbanairship.debug.DebugManager.Companion Companion = null;
    
    @java.lang.Override()
    protected void onAirshipReady(@org.jetbrains.annotations.NotNull()
    com.urbanairship.UAirship airship) {
    }
    
    public DebugManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.urbanairship.PreferenceDataStore preferenceDataStore) {
        super(null, null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/urbanairship/debug/DebugManager$Companion;", "", "()V", "TRIM_PUSHES_COUNT", "", "urbanairship-debug_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}