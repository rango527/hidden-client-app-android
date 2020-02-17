package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.ProcessStageEntity

@Dao
interface ProcessStageDao {

    @get:Query("SELECT * FROM ProcessStage")
    val all: List<ProcessStageEntity>

    @Query("SELECT * FROM ProcessStage WHERE pProcessId = :processId")
    fun getStageByProcess(processId: Int): List<ProcessStageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg processStage: ProcessStageEntity)

    @Query("DELETE FROM ProcessStage")
    fun deleteAll()

    @Query("DELETE FROM ProcessStage WHERE pProcessId = :processId")
    fun deleteByProcessId(processId: Int)
}