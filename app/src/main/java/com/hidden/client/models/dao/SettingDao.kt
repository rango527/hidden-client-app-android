package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.SettingEntity

@Dao
interface SettingDao {

    @get:Query("SELECT * FROM Setting")
    val all: List<SettingEntity>

    @Query("SELECT * FROM Setting WHERE myId = :myId AND jobId = :jobId")
    fun getMyJobSettingByJobId(myId: Int, jobId: Int): List<SettingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg settings: SettingEntity)

    @Query("DELETE FROM Setting")
    fun deleteAll()
}