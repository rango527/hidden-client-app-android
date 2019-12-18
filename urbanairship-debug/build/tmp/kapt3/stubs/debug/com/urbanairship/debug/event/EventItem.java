package com.urbanairship.debug.event;

import java.lang.System;

/**
 * *
 * Event item used in the event adapter.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001b\u0010\u000b\u001a\u00020\f8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\n\u00a8\u0006\u0017"}, d2 = {"Lcom/urbanairship/debug/event/EventItem;", "", "eventEntity", "Lcom/urbanairship/debug/event/persistence/EventEntity;", "(Lcom/urbanairship/debug/event/persistence/EventEntity;)V", "getEventEntity", "()Lcom/urbanairship/debug/event/persistence/EventEntity;", "id", "", "getId", "()Ljava/lang/String;", "payload", "Lcom/urbanairship/json/JsonMap;", "getPayload", "()Lcom/urbanairship/json/JsonMap;", "payload$delegate", "Lkotlin/Lazy;", "time", "", "getTime", "()J", "type", "getType", "urbanairship-debug_debug"})
public final class EventItem {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy payload$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String type = null;
    private final long time = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final com.urbanairship.debug.event.persistence.EventEntity eventEntity = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.urbanairship.json.JsonMap getPayload() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getType() {
        return null;
    }
    
    public final long getTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.urbanairship.debug.event.persistence.EventEntity getEventEntity() {
        return null;
    }
    
    public EventItem(@org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.event.persistence.EventEntity eventEntity) {
        super();
    }
}