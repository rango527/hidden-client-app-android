package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.JobSettingEntity

@Dao
interface JobSettingDao {

    @get:Query("SELECT * FROM JobSetting")
    val all: List<JobSettingEntity>

    @Query("SELECT * FROM JobSetting WHERE myId = :myId AND jobId = :jobId")
    fun getMyJobSettingByJobId(myId: Int, jobId: Int): List<JobSettingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg jobSettings: JobSettingEntity)

    @Query("DELETE FROM JobSetting")
    fun deleteAll()
}