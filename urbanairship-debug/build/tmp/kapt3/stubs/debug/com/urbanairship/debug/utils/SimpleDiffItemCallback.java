package com.urbanairship.debug.utils;

import java.lang.System;

/**
 * Item callback that checks the object equality.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u001d\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\bJ\u001d\u0010\t\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\n"}, d2 = {"Lcom/urbanairship/debug/utils/SimpleDiffItemCallback;", "T", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "()V", "areContentsTheSame", "", "t", "t1", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "areItemsTheSame", "urbanairship-debug_debug"})
public final class SimpleDiffItemCallback<T extends java.lang.Object> extends androidx.recyclerview.widget.DiffUtil.ItemCallback<T> {
    
    @java.lang.Override()
    public boolean areItemsTheSame(T t, T t1) {
        return false;
    }
    
    @java.lang.Override()
    public boolean areContentsTheSame(T t, T t1) {
        return false;
    }
    
    public SimpleDiffItemCallback() {
        super();
    }
}