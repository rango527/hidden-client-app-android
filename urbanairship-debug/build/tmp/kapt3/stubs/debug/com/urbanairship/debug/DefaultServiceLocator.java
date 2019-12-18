package com.urbanairship.debug;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0012\u001a\u00020\u00138VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\f\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001f"}, d2 = {"Lcom/urbanairship/debug/DefaultServiceLocator;", "Lcom/urbanairship/debug/ServiceLocator;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "eventDatabase", "Lcom/urbanairship/debug/event/persistence/EventDatabase;", "getEventDatabase", "()Lcom/urbanairship/debug/event/persistence/EventDatabase;", "eventDatabase$delegate", "Lkotlin/Lazy;", "pushDatabase", "Lcom/urbanairship/debug/push/persistence/PushDatabase;", "getPushDatabase", "()Lcom/urbanairship/debug/push/persistence/PushDatabase;", "pushDatabase$delegate", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "sharedPreferences$delegate", "getEventDao", "Lcom/urbanairship/debug/event/persistence/EventDao;", "getEventRepository", "Lcom/urbanairship/debug/event/EventRepository;", "getPushDao", "Lcom/urbanairship/debug/push/persistence/PushDao;", "getPushRepository", "Lcom/urbanairship/debug/push/PushRepository;", "urbanairship-debug_debug"})
public final class DefaultServiceLocator implements com.urbanairship.debug.ServiceLocator {
    private final kotlin.Lazy eventDatabase$delegate = null;
    private final kotlin.Lazy pushDatabase$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy sharedPreferences$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    private final com.urbanairship.debug.event.persistence.EventDatabase getEventDatabase() {
        return null;
    }
    
    private final com.urbanairship.debug.push.persistence.PushDatabase getPushDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.urbanairship.debug.event.EventRepository getEventRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.urbanairship.debug.push.PushRepository getPushRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.urbanairship.debug.event.persistence.EventDao getEventDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.urbanairship.debug.push.persistence.PushDao getPushDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.content.SharedPreferences getSharedPreferences() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    public DefaultServiceLocator(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
}