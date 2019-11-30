package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.CandidateBrandEntity
import com.hidden.client.models.ShortlistCandidateEntity

@Dao
interface ShortlistCandidateDao {

    @get:Query("SELECT * FROM ShortlistCandidate")
    val all: List<ShortlistCandidateEntity>

    @Query("SELECT * FROM ShortlistCandidate WHERE pShortlistId = :shortlistId")
    fun getCandidateByShortlistId(shortlistId: Int): List<CandidateBrandEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg shortlistCandidateList: ShortlistCandidateEntity)

    @Query("DELETE FROM ShortlistCandidate")
    fun deleteAll()

    @Query("DELETE FROM ShortlistCandidate WHERE pShortlistId = :shortlistId")
    fun deleteByShortlistId(shortlistId: Int)
}