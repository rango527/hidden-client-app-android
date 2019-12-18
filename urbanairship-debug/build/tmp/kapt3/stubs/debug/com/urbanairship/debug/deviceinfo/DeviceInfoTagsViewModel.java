package com.urbanairship.debug.deviceinfo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005J\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\rJ\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005J\b\u0010\u000f\u001a\u00020\nH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/urbanairship/debug/deviceinfo/DeviceInfoTagsViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "tags", "", "", "tagsLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "addTag", "", "tag", "getTags", "Landroidx/lifecycle/LiveData;", "removeTag", "updateList", "urbanairship-debug_debug"})
public final class DeviceInfoTagsViewModel extends androidx.lifecycle.ViewModel {
    private final androidx.lifecycle.MutableLiveData<java.util.List<java.lang.String>> tagsLiveData = null;
    private final java.util.List<java.lang.String> tags = null;
    
    /**
     * Gets the tags live data.
     *
     * @return The tags live data.
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<java.lang.String>> getTags() {
        return null;
    }
    
    /**
     * Adds a channel tag to Urban Airship.
     *
     * @param tag The tag.
     */
    public final void addTag(@org.jetbrains.annotations.NotNull()
    java.lang.String tag) {
    }
    
    /**
     * Removes a channel tag from Urban Airship.
     *
     * @param tag The tag.
     */
    public final void removeTag(@org.jetbrains.annotations.NotNull()
    java.lang.String tag) {
    }
    
    private final void updateList() {
    }
    
    public DeviceInfoTagsViewModel() {
        super();
    }
}