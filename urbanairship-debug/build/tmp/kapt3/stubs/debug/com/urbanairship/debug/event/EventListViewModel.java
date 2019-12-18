package com.urbanairship.debug.event;

import java.lang.System;

/**
 * Event list view model.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0014\u001a\u00020\u0015R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0017"}, d2 = {"Lcom/urbanairship/debug/event/EventListViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/urbanairship/debug/event/EventRepository;", "(Lcom/urbanairship/debug/event/EventRepository;)V", "activeFilters", "Ljava/util/ArrayList;", "Lcom/urbanairship/debug/event/EventFilter;", "activeFiltersLiveData", "Landroidx/lifecycle/MediatorLiveData;", "", "getActiveFiltersLiveData", "()Landroidx/lifecycle/MediatorLiveData;", "events", "Landroidx/paging/PagedList;", "Lcom/urbanairship/debug/event/persistence/EventEntity;", "getEvents", "filters", "getFilters", "()Ljava/util/List;", "clearFilters", "", "Companion", "urbanairship-debug_debug"})
public final class EventListViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.urbanairship.debug.event.EventFilter> filters = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MediatorLiveData<androidx.paging.PagedList<com.urbanairship.debug.event.persistence.EventEntity>> events = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MediatorLiveData<java.util.List<com.urbanairship.debug.event.EventFilter>> activeFiltersLiveData = null;
    private java.util.ArrayList<com.urbanairship.debug.event.EventFilter> activeFilters;
    private static final int PAGE_SIZE = 30;
    public static final com.urbanairship.debug.event.EventListViewModel.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.urbanairship.debug.event.EventFilter> getFilters() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MediatorLiveData<androidx.paging.PagedList<com.urbanairship.debug.event.persistence.EventEntity>> getEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MediatorLiveData<java.util.List<com.urbanairship.debug.event.EventFilter>> getActiveFiltersLiveData() {
        return null;
    }
    
    public final void clearFilters() {
    }
    
    public EventListViewModel(@org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.event.EventRepository repository) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/urbanairship/debug/event/EventListViewModel$Companion;", "", "()V", "PAGE_SIZE", "", "urbanairship-debug_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}