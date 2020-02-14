package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.ReviewerEntity

@Dao
interface ReviewerDao {

    @get:Query("SELECT * FROM Reviewer")
    val all: List<ReviewerEntity>

    @Query("SELECT * FROM Reviewer WHERE myId=:myId")
    fun getMyReviewers(myId: Int): List<ReviewerEntity>

    @Query("SELECT * FROM Reviewer WHERE myId = :myId AND jobId = :jobId")
    fun getReviewerByJobId(myId: Int, jobId: Int): List<ReviewerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg reviewers: ReviewerEntity)

    @Query("DELETE FROM Reviewer")
    fun deleteAll()
}