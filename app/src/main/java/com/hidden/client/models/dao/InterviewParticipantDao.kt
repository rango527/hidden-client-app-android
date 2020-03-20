package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.FeedbackIDEntity
import com.hidden.client.models.entity.InterviewParticipantEntity
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.models.entity.TimelineEntity

@Dao
interface InterviewParticipantDao {

    @get:Query("SELECT * FROM InterviewParticipant")
    val all: List<InterviewParticipantEntity>

    @Query("SELECT * FROM InterviewParticipant WHERE pTimelineId = :timelineId")
    fun getInterviewParticipant(timelineId: Int): List<InterviewParticipantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg interviewParticipant: InterviewParticipantEntity)

    @Query("DELETE FROM InterviewParticipant WHERE pProcessId = :processId")
    fun deleteByProcessId(processId: Int)
}