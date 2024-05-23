package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.FeedbackIDEntity
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.models.entity.TimelineEntity

@Dao
interface FeedbackIDDao {

    @get:Query("SELECT * FROM FeedbackID")
    val all: List<FeedbackIDEntity>

    @Query("SELECT * FROM FeedbackID WHERE pTimelineId = :timelineId")
    fun getFeedback(timelineId: Int): List<FeedbackIDEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg feedback: FeedbackIDEntity)

    @Query("DELETE FROM FeedbackID")
    fun deleteAll()

    @Query("DELETE FROM FeedbackID WHERE pProcessId = :processId")
    fun deleteByProcessId(processId: Int)
}