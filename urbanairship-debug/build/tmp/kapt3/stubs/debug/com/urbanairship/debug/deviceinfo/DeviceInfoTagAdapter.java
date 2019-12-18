package com.urbanairship.debug.deviceinfo;

import java.lang.System;

/**
 * RecyclerView adapter for a list of tags.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\r\u000eB\u0005\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\n\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\u000f"}, d2 = {"Lcom/urbanairship/debug/deviceinfo/DeviceInfoTagAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "", "Lcom/urbanairship/debug/deviceinfo/DeviceInfoTagAdapter$ViewHolder;", "()V", "onBindViewHolder", "", "viewHolder", "i", "", "onCreateViewHolder", "viewGroup", "Landroid/view/ViewGroup;", "TagFilterDiff", "ViewHolder", "urbanairship-debug_debug"})
public final class DeviceInfoTagAdapter extends androidx.recyclerview.widget.ListAdapter<java.lang.String, com.urbanairship.debug.deviceinfo.DeviceInfoTagAdapter.ViewHolder> {
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.urbanairship.debug.deviceinfo.DeviceInfoTagAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup viewGroup, int i) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.deviceinfo.DeviceInfoTagAdapter.ViewHolder viewHolder, int i) {
    }
    
    public DeviceInfoTagAdapter() {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u00068F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2 = {"Lcom/urbanairship/debug/deviceinfo/DeviceInfoTagAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/urbanairship/debug/databinding/UaItemTagBinding;", "(Lcom/urbanairship/debug/deviceinfo/DeviceInfoTagAdapter;Lcom/urbanairship/debug/databinding/UaItemTagBinding;)V", "tag", "", "getTag", "()Ljava/lang/String;", "bind", "", "urbanairship-debug_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.urbanairship.debug.databinding.UaItemTagBinding binding = null;
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getTag() {
            return null;
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        java.lang.String tag) {
        }
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        com.urbanairship.debug.databinding.UaItemTagBinding binding) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/urbanairship/debug/deviceinfo/DeviceInfoTagAdapter$TagFilterDiff;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "", "()V", "areContentsTheSame", "", "s", "t1", "areItemsTheSame", "urbanairship-debug_debug"})
    public static final class TagFilterDiff extends androidx.recyclerview.widget.DiffUtil.ItemCallback<java.lang.String> {
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        java.lang.String s, @org.jetbrains.annotations.NotNull()
        java.lang.String t1) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        java.lang.String s, @org.jetbrains.annotations.NotNull()
        java.lang.String t1) {
            return false;
        }
        
        public TagFilterDiff() {
            super();
        }
    }
}