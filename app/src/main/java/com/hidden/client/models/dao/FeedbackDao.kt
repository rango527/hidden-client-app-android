package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.FeedbackEntity

@Dao
interface FeedbackDao {

    @get:Query("SELECT * FROM Feedback")
    val all: List<FeedbackEntity>

    @Query("SELECT * FROM Feedback WHERE pCandidateId = :candidateId")
    fun getFeedbackByCandidateId(candidateId: Int): List<FeedbackEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg feedbackList: FeedbackEntity)

    @Query("DELETE FROM Feedback")
    fun deleteAll()

    @Query("DELETE FROM Feedback WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}