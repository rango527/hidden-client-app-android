package com.urbanairship.debug.event;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 2, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u001a\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00072\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u001a\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00072\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0018\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\u0018\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007\u001a\u0018\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007\u001a\u0018\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007\u001a\u0018\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0014H\u0007\u00a8\u0006\u001a"}, d2 = {"bindBackgroundTint", "", "view", "Landroid/view/View;", "color", "", "bindChipFilterIconTint", "Lcom/google/android/material/chip/Chip;", "bindChipIconColor", "bindEventFilter", "eventFilter", "Lcom/urbanairship/debug/event/EventFilter;", "bindEventName", "Landroid/widget/TextView;", "item", "Lcom/urbanairship/debug/event/EventItem;", "bindFabVisibility", "fab", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "visible", "", "bindFormatTime", "time", "", "showHide", "show", "urbanairship-debug_debug"})
public final class BindingAdaptersKt {
    
    @androidx.databinding.BindingAdapter(value = {"fabVisibility"})
    public static final void bindFabVisibility(@org.jetbrains.annotations.NotNull()
    com.google.android.material.floatingactionbutton.FloatingActionButton fab, boolean visible) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"visible"})
    public static final void showHide(@org.jetbrains.annotations.NotNull()
    android.view.View view, boolean show) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"backgroundTint"})
    public static final void bindBackgroundTint(@org.jetbrains.annotations.NotNull()
    android.view.View view, @androidx.annotation.ColorInt()
    int color) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"formatTime"})
    public static final void bindFormatTime(@org.jetbrains.annotations.NotNull()
    android.widget.TextView view, long time) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"chipIconColor"})
    public static final void bindChipIconColor(@org.jetbrains.annotations.NotNull()
    com.google.android.material.chip.Chip view, @androidx.annotation.ColorInt()
    int color) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"eventFilter"})
    public static final void bindEventFilter(@org.jetbrains.annotations.NotNull()
    com.google.android.material.chip.Chip view, @org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.event.EventFilter eventFilter) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"eventName"})
    public static final void bindEventName(@org.jetbrains.annotations.NotNull()
    android.widget.TextView view, @org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.event.EventItem item) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"checkedIconTint"})
    public static final void bindChipFilterIconTint(@org.jetbrains.annotations.NotNull()
    com.google.android.material.chip.Chip view, @androidx.annotation.ColorInt()
    int color) {
    }
}