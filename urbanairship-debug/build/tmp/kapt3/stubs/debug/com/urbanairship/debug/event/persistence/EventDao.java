package com.urbanairship.debug.event.persistence;

import java.lang.System;

/**
 * Data Access Object for the event table.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0014\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00040\bH\'J\"\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00040\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\'J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004H\'J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\'J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\tH\'\u00a8\u0006\u0014"}, d2 = {"Lcom/urbanairship/debug/event/persistence/EventDao;", "", "getEvent", "Landroidx/lifecycle/LiveData;", "Lcom/urbanairship/debug/event/persistence/EventEntity;", "eventId", "", "getEvents", "Landroidx/paging/DataSource$Factory;", "", "types", "", "insertEvent", "", "event", "trimEvents", "count", "", "trimOldEvents", "days", "urbanairship-debug_debug"})
public abstract interface EventDao {
    
    @androidx.room.Insert()
    public abstract void insertEvent(@org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.event.persistence.EventEntity event);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM events ORDER BY id DESC")
    public abstract androidx.paging.DataSource.Factory<java.lang.Integer, com.urbanairship.debug.event.persistence.EventEntity> getEvents();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM events WHERE type IN(:types) ORDER BY id DESC")
    public abstract androidx.paging.DataSource.Factory<java.lang.Integer, com.urbanairship.debug.event.persistence.EventEntity> getEvents(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> types);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "select * from events where eventId = :eventId")
    public abstract androidx.lifecycle.LiveData<com.urbanairship.debug.event.persistence.EventEntity> getEvent(@org.jetbrains.annotations.NotNull()
    java.lang.String eventId);
    
    @androidx.room.Query(value = "DELETE FROM events where eventId NOT IN (SELECT eventId from events ORDER BY time LIMIT :count)")
    public abstract void trimEvents(long count);
    
    @androidx.room.Query(value = "DELETE FROM events WHERE datetime( time / 1000 , \'unixepoch\') < datetime(\'now\', \'-\' || :days || \' day\')")
    public abstract void trimOldEvents(int days);
}