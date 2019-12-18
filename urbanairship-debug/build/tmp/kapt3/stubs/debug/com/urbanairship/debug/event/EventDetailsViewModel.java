package com.urbanairship.debug.event;

import java.lang.System;

/**
 * View model for event details.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\bR\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/urbanairship/debug/event/EventDetailsViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/urbanairship/debug/event/EventRepository;", "eventId", "", "(Lcom/urbanairship/debug/event/EventRepository;Ljava/lang/String;)V", "eventEntity", "Landroidx/lifecycle/LiveData;", "Lcom/urbanairship/debug/event/persistence/EventEntity;", "eventData", "urbanairship-debug_debug"})
public final class EventDetailsViewModel extends androidx.lifecycle.ViewModel {
    private final androidx.lifecycle.LiveData<com.urbanairship.debug.event.persistence.EventEntity> eventEntity = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> eventData() {
        return null;
    }
    
    public EventDetailsViewModel(@org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.event.EventRepository repository, @org.jetbrains.annotations.NotNull()
    java.lang.String eventId) {
        super();
    }
}