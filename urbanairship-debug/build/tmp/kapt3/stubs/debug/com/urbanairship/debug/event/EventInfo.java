package com.urbanairship.debug.event;

import java.lang.System;

/**
 * Event colors, initials, details helpers.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/urbanairship/debug/event/EventInfo;", "", "()V", "Companion", "urbanairship-debug_debug"})
public final class EventInfo {
    private static final java.lang.String LOCATION_EVENT = "location";
    private static final java.lang.String REGION_EVENT = "region_event";
    private static final java.lang.String SCREEN_EVENT = "screen_tracking";
    private static final java.lang.String CUSTOM_EVENT = "custom_event";
    private static final java.lang.String IAA_RESOLUTION_EVENT = "in_app_resolution";
    private static final java.lang.String IAA_DISPLAY_EVENT = "in_app_display";
    private static final java.lang.String APP_BACKGROUND_EVENT = "app_background";
    private static final java.lang.String APP_FOREGROUND_EVENT = "app_foreground";
    private static final java.lang.String PUSH_ARRIVED_EVENT = "push_arrived";
    private static final java.lang.String ASSOCIATE_IDENTIFIERS_EVENT = "associate_identifiers";
    private static final java.lang.String INSTALL_ATTRIBUTION_EVENT = "install_attribution";
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> KNOWN_TYPES = null;
    public static final com.urbanairship.debug.event.EventInfo.Companion Companion = null;
    
    public EventInfo() {
        super();
    }
    
    @androidx.annotation.ColorInt()
    public static final int getColor(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String type) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getEventInitials(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0004H\u0007J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0004H\u0007J \u0010\u0019\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0004H\u0007J\u0016\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0004J\n\u0010\u001e\u001a\u00020\u0004*\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/urbanairship/debug/event/EventInfo$Companion;", "", "()V", "APP_BACKGROUND_EVENT", "", "APP_FOREGROUND_EVENT", "ASSOCIATE_IDENTIFIERS_EVENT", "CUSTOM_EVENT", "IAA_DISPLAY_EVENT", "IAA_RESOLUTION_EVENT", "INSTALL_ATTRIBUTION_EVENT", "KNOWN_TYPES", "", "getKNOWN_TYPES", "()Ljava/util/List;", "LOCATION_EVENT", "PUSH_ARRIVED_EVENT", "REGION_EVENT", "SCREEN_EVENT", "getColor", "", "context", "Landroid/content/Context;", "type", "getColorRes", "getDetailedEventName", "eventData", "Lcom/urbanairship/json/JsonMap;", "getEventInitials", "getEventName", "firstOrEmpty", "urbanairship-debug_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getKNOWN_TYPES() {
            return null;
        }
        
        @androidx.annotation.ColorInt()
        public final int getColor(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.lang.String type) {
            return 0;
        }
        
        @androidx.annotation.ColorRes()
        public final int getColorRes(@org.jetbrains.annotations.NotNull()
        java.lang.String type) {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getEventName(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.lang.String type) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getEventInitials(@org.jetbrains.annotations.NotNull()
        java.lang.String type) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getDetailedEventName(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.lang.String type, @org.jetbrains.annotations.NotNull()
        com.urbanairship.json.JsonMap eventData) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String firstOrEmpty(@org.jetbrains.annotations.NotNull()
        java.lang.String $this$firstOrEmpty) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}