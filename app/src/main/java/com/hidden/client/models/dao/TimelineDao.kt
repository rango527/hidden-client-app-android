package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.models.entity.TimelineEntity

@Dao
interface TimelineDao {

    @get:Query("SELECT * FROM Timeline")
    val all: List<TimelineEntity>

    @Query("SELECT * FROM Timeline WHERE pProcessId = :processId")
    fun getTimeline(processId: Int): List<TimelineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg timeline: TimelineEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timeline: TimelineEntity)

    @Query("DELETE FROM Timeline WHERE pProcessId = :processId")
    fun deleteByProcessId(processId: Int)
}