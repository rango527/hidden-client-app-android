package com.urbanairship.debug.event;

import java.lang.System;

/**
 * Event repository.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\b2\u0006\u0010\n\u001a\u00020\u000bJ\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\t0\rJ \u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\t0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0014"}, d2 = {"Lcom/urbanairship/debug/event/EventRepository;", "", "dao", "Lcom/urbanairship/debug/event/persistence/EventDao;", "(Lcom/urbanairship/debug/event/persistence/EventDao;)V", "getDao", "()Lcom/urbanairship/debug/event/persistence/EventDao;", "getEvent", "Landroidx/lifecycle/LiveData;", "Lcom/urbanairship/debug/event/persistence/EventEntity;", "eventId", "", "getEvents", "Landroidx/paging/DataSource$Factory;", "", "types", "", "trimOldEvents", "", "days", "urbanairship-debug_debug"})
public final class EventRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.urbanairship.debug.event.persistence.EventDao dao = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.paging.DataSource.Factory<java.lang.Integer, com.urbanairship.debug.event.persistence.EventEntity> getEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.paging.DataSource.Factory<java.lang.Integer, com.urbanairship.debug.event.persistence.EventEntity> getEvents(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> types) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.urbanairship.debug.event.persistence.EventEntity> getEvent(@org.jetbrains.annotations.NotNull()
    java.lang.String eventId) {
        return null;
    }
    
    public final void trimOldEvents(int days) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.urbanairship.debug.event.persistence.EventDao getDao() {
        return null;
    }
    
    public EventRepository(@org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.event.persistence.EventDao dao) {
        super();
    }
}