package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.helpers.Enums
import com.hidden.client.models.entity.ReviewerEntity

@Dao
interface ReviewerDao {

    @get:Query("SELECT * FROM Reviewer")
    val all: List<ReviewerEntity>

    @Query("SELECT * FROM Reviewer WHERE myId = :myId AND parentId = :parentId AND settingType = :settingType")
    fun getReviewerByParentId(settingType: Int, parentId: Int, myId: Int): List<ReviewerEntity>

    @Query("SELECT * FROM Reviewer WHERE myId = :myId AND parentId = :parentId AND reviewerType = :reviewType AND settingType = :settingType")
    fun getReviewerByParentIdAndReviewType(settingType: Int, reviewType:Int, parentId: Int, myId: Int): List<ReviewerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg reviewers: ReviewerEntity)

    @Query("DELETE FROM Reviewer WHERE settingType = :settingType")
    fun deleteAll(settingType: Int)

    @Query("DELETE FROM Reviewer WHERE id = :id")
    fun deleteById(id: Int)
}