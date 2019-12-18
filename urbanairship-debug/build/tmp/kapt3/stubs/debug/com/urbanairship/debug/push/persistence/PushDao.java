package com.urbanairship.debug.push.persistence;

import java.lang.System;

/**
 * Data Access Object for the push table.
 * @hide
 */
@androidx.annotation.RestrictTo(value = {androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\bg\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0014\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00040\bH\'J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\'J\u0010\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\'\u00a8\u0006\u0010"}, d2 = {"Lcom/urbanairship/debug/push/persistence/PushDao;", "", "getPush", "Landroidx/lifecycle/LiveData;", "Lcom/urbanairship/debug/push/persistence/PushEntity;", "pushId", "", "getPushes", "Landroidx/paging/DataSource$Factory;", "", "insertPush", "", "push", "trimPushes", "count", "", "urbanairship-debug_debug"})
public abstract interface PushDao {
    
    @androidx.room.Insert()
    public abstract void insertPush(@org.jetbrains.annotations.NotNull()
    com.urbanairship.debug.push.persistence.PushEntity push);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM pushes ORDER BY id DESC")
    public abstract androidx.paging.DataSource.Factory<java.lang.Integer, com.urbanairship.debug.push.persistence.PushEntity> getPushes();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM pushes WHERE pushId = :pushId")
    public abstract androidx.lifecycle.LiveData<com.urbanairship.debug.push.persistence.PushEntity> getPush(@org.jetbrains.annotations.NotNull()
    java.lang.String pushId);
    
    @androidx.room.Query(value = "DELETE FROM pushes where id NOT IN (SELECT id from pushes ORDER BY time LIMIT :count)")
    public abstract void trimPushes(long count);
}