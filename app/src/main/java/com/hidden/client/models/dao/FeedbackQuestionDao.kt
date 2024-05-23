package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.FeedbackQuestionEntity

@Dao
interface FeedbackQuestionDao {

    @get:Query("SELECT * FROM FeedbackQuestion")
    val all: List<FeedbackQuestionEntity>

    @Query("SELECT * FROM FeedbackQuestion WHERE pFeedbackId = :feedbackId")
    fun getFeedbackQuestionByFeedbackId(feedbackId: Int): List<FeedbackQuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg feedbackQuestionList: FeedbackQuestionEntity)

    @Query("DELETE FROM FeedbackQuestion")
    fun deleteAll()

    @Query("DELETE FROM FeedbackQuestion WHERE pFeedbackId = :feedbackId")
    fun deleteByFeedbackId(feedbackId: Int)
}