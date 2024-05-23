package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.ProcessSettingEntity

@Dao
interface ProcessSettingDao {

    @get:Query("SELECT * FROM ProcessSetting")
    val all: List<ProcessSettingEntity>

    @Query("SELECT * FROM ProcessSetting WHERE myId = :myId AND processId = :processId")
    fun getMyProcessSettingByProcessId(myId: Int, processId: Int): List<ProcessSettingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg processSettings: ProcessSettingEntity)

    @Query("DELETE FROM ProcessSetting")
    fun deleteAll()
}