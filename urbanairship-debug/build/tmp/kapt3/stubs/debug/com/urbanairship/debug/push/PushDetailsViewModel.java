package com.urbanairship.debug.push;

import java.lang.System;

/**
 * View model for event details.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\bR\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\r"}, d2 = {"Lcom/urbanairship/debug/push/PushDetailsViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/urbanairship/debug/push/PushRepository;", "pushId", "", "(Lcom/urbanairship/debug/push/PushRepository;Ljava/lang/String;)V", "pushEntity", "Landroidx/lifecycle/LiveData;", "Lcom/urbanairship/debug/push/persistence/PushEntity;", "getPushId", "()Ljava/lang/String;", "pushData", "urbanairship-debug_debug"})
public final class PushDetailsViewModel extends androidx.lifecycle.ViewModel {
    private final androidx.lifecycle.LiveData<com.urbanairship.debug.push.persistence.PushEntity> pushEntity = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String pushId = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> pushData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPushId() {
        return null;
    }
    
    public PushDetailsViewModel(@org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.push.PushRepository repository, @org.jetbrains.annotations.NotNull()
    java.lang.String pushId) {
        super();
    }
}