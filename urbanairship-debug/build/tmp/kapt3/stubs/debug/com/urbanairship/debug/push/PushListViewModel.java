package com.urbanairship.debug.push;

import java.lang.System;

/**
 * PushItem list view model.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \f2\u00020\u0001:\u0001\fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R;\u0010\u0005\u001a,\u0012(\u0012&\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b \t*\u0012\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b\u0018\u00010\u00070\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\r"}, d2 = {"Lcom/urbanairship/debug/push/PushListViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/urbanairship/debug/push/PushRepository;", "(Lcom/urbanairship/debug/push/PushRepository;)V", "pushes", "Landroidx/lifecycle/LiveData;", "Landroidx/paging/PagedList;", "Lcom/urbanairship/debug/push/persistence/PushEntity;", "kotlin.jvm.PlatformType", "getPushes", "()Landroidx/lifecycle/LiveData;", "Companion", "urbanairship-debug_debug"})
public final class PushListViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<androidx.paging.PagedList<com.urbanairship.debug.push.persistence.PushEntity>> pushes = null;
    private static final int PAGE_SIZE = 30;
    public static final com.urbanairship.debug.push.PushListViewModel.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<androidx.paging.PagedList<com.urbanairship.debug.push.persistence.PushEntity>> getPushes() {
        return null;
    }
    
    public PushListViewModel(@org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.push.PushRepository repository) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/urbanairship/debug/push/PushListViewModel$Companion;", "", "()V", "PAGE_SIZE", "", "urbanairship-debug_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}