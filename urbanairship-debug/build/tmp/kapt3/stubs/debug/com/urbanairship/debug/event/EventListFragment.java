package com.urbanairship.debug.event;

import java.lang.System;

/**
 * Event list fragment.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 32\u00020\u0001:\u000234B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u001eH\u0002J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J&\u0010&\u001a\u0004\u0018\u00010\'2\u0006\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010+2\b\u0010,\u001a\u0004\u0018\u00010-H\u0016J\u001a\u0010.\u001a\u00020#2\u0006\u0010/\u001a\u00020\'2\b\u0010,\u001a\u0004\u0018\u00010-H\u0016J\u0010\u00100\u001a\u00020#2\u0006\u00101\u001a\u00020\u001eH\u0002J\u0006\u00102\u001a\u00020#R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R$\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00118B@BX\u0082\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001b\u0010\u0017\u001a\u00020\u00188BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0019\u0010\u001a\u00a8\u00065"}, d2 = {"Lcom/urbanairship/debug/event/EventListFragment;", "Landroidx/fragment/app/Fragment;", "()V", "areFiltersActive", "", "getAreFiltersActive", "()Z", "bottomSheetBehavior", "Lcom/google/android/material/bottomsheet/BottomSheetBehavior;", "collapseAlpha", "Landroidx/databinding/ObservableFloat;", "expandAlpha", "isFilterSheetVisible", "Landroidx/databinding/ObservableBoolean;", "sharedPreferences", "Landroid/content/SharedPreferences;", "days", "", "storageDays", "getStorageDays", "()I", "setStorageDays", "(I)V", "viewModel", "Lcom/urbanairship/debug/event/EventListViewModel;", "getViewModel", "()Lcom/urbanairship/debug/event/EventListViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "offsetToAlpha", "", "value", "rangeMin", "rangeMax", "onAttach", "", "context", "Landroid/content/Context;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "updateCollapseAlpha", "slideOffset", "updateFiltersLayout", "Companion", "ViewModelFactory", "urbanairship-debug_debug"})
public final class EventListFragment extends androidx.fragment.app.Fragment {
    private com.google.android.material.bottomsheet.BottomSheetBehavior<?> bottomSheetBehavior;
    private final kotlin.Lazy viewModel$delegate = null;
    private androidx.databinding.ObservableFloat collapseAlpha;
    private androidx.databinding.ObservableFloat expandAlpha;
    private androidx.databinding.ObservableBoolean isFilterSheetVisible;
    private android.content.SharedPreferences sharedPreferences;
    private static final float COLLAPSE_CHANGEOVER_THRESHOLD = 0.4F;
    private static final float COLLAPSE_MAX_THRESHOLD = 0.67F;
    private static final float EXPAND_MAX_THRESHOLD = 0.0F;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String STORAGE_DAYS_KEY = "com.urbanairship.debug.event.STORAGE_DAYS";
    public static final int DEFAULT_STORAGE_DAYS = 30;
    public static final com.urbanairship.debug.event.EventListFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    private final com.urbanairship.debug.event.EventListViewModel getViewModel() {
        return null;
    }
    
    private final boolean getAreFiltersActive() {
        return false;
    }
    
    private final int getStorageDays() {
        return 0;
    }
    
    private final void setStorageDays(int days) {
    }
    
    @java.lang.Override()
    public void onAttach(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void updateCollapseAlpha(float slideOffset) {
    }
    
    /**
     * Map a slideOffset (in the range `[-1, 1]`) to an alpha value based on the desired range.
     * For example, `offsetToAlpha(0.5, 0.25, 1) = 0.33` because 0.5 is 1/3 of the way between 0.25
     * and 1. The result value is additionally clamped to the range `[0, 1]`.
     *
     * Taken from https://github.com/google/iosched/master/mobile/src/nav_home/java/com/google/samples/apps/iosched/ui/schedule/filters/ScheduleFilterFragment.kt
     */
    private final float offsetToAlpha(float value, float rangeMin, float rangeMax) {
        return 0.0F;
    }
    
    public final void updateFiltersLayout() {
    }
    
    public EventListFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J%\u0010\u0005\u001a\u0002H\u0006\"\b\b\u0000\u0010\u0006*\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00060\tH\u0016\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/urbanairship/debug/event/EventListFragment$ViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "urbanairship-debug_debug"})
    public static final class ViewModelFactory implements androidx.lifecycle.ViewModelProvider.Factory {
        private final android.content.Context context = null;
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public <T extends androidx.lifecycle.ViewModel>T create(@org.jetbrains.annotations.NotNull()
        java.lang.Class<T> modelClass) {
            return null;
        }
        
        public ViewModelFactory(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/urbanairship/debug/event/EventListFragment$Companion;", "", "()V", "COLLAPSE_CHANGEOVER_THRESHOLD", "", "COLLAPSE_MAX_THRESHOLD", "DEFAULT_STORAGE_DAYS", "", "EXPAND_MAX_THRESHOLD", "STORAGE_DAYS_KEY", "", "urbanairship-debug_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}