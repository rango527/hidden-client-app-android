package com.urbanairship.debug.push;

import java.lang.System;

/**
 * *
 * PushItem model object.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u00068F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/urbanairship/debug/push/PushItem;", "", "pushEntity", "Lcom/urbanairship/debug/push/persistence/PushEntity;", "(Lcom/urbanairship/debug/push/persistence/PushEntity;)V", "alert", "", "getAlert", "()Ljava/lang/String;", "message", "Lcom/urbanairship/push/PushMessage;", "getMessage", "()Lcom/urbanairship/push/PushMessage;", "message$delegate", "Lkotlin/Lazy;", "getPushEntity", "()Lcom/urbanairship/debug/push/persistence/PushEntity;", "pushId", "getPushId", "time", "", "getTime", "()J", "urbanairship-debug_debug"})
public final class PushItem {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy message$delegate = null;
    private final long time = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String pushId = null;
    @org.jetbrains.annotations.NotNull()
    private final com.urbanairship.debug.push.persistence.PushEntity pushEntity = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.urbanairship.push.PushMessage getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAlert() {
        return null;
    }
    
    public final long getTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPushId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.urbanairship.debug.push.persistence.PushEntity getPushEntity() {
        return null;
    }
    
    public PushItem(@org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.push.persistence.PushEntity pushEntity) {
        super();
    }
}