package com.urbanairship.debug;

import java.lang.System;

/**
 * Service locator. Eventually we will want to use a proper DI framework.
 * @hide
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b`\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eJ\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\rH&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u000f"}, d2 = {"Lcom/urbanairship/debug/ServiceLocator;", "", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "getEventDao", "Lcom/urbanairship/debug/event/persistence/EventDao;", "getEventRepository", "Lcom/urbanairship/debug/event/EventRepository;", "getPushDao", "Lcom/urbanairship/debug/push/persistence/PushDao;", "getPushRepository", "Lcom/urbanairship/debug/push/PushRepository;", "Companion", "urbanairship-debug_debug"})
public abstract interface ServiceLocator {
    public static final com.urbanairship.debug.ServiceLocator.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREFERENCES_KEY = "com.urbanairship.debug";
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.urbanairship.debug.event.EventRepository getEventRepository();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.urbanairship.debug.push.PushRepository getPushRepository();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.urbanairship.debug.event.persistence.EventDao getEventDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.urbanairship.debug.push.persistence.PushDao getPushDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract android.content.SharedPreferences getSharedPreferences();
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/urbanairship/debug/ServiceLocator$Companion;", "", "()V", "PREFERENCES_KEY", "", "instance", "Lcom/urbanairship/debug/ServiceLocator;", "shared", "context", "Landroid/content/Context;", "urbanairship-debug_debug"})
    public static final class Companion {
        private static volatile com.urbanairship.debug.ServiceLocator instance;
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String PREFERENCES_KEY = "com.urbanairship.debug";
        
        @org.jetbrains.annotations.NotNull()
        public final com.urbanairship.debug.ServiceLocator shared(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}