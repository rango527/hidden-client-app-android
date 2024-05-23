package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.ShortlistCandidateEntity

@Dao
interface ShortlistCandidateDao {

    @get:Query("SELECT * FROM ShortlistCandidate")
    val all: List<ShortlistCandidateEntity>

    @Query("SELECT * FROM ShortlistCandidate WHERE pClientId = :clientId")
    fun getCandidateByClientId(clientId: Int): List<ShortlistCandidateEntity>

    @Query("SELECT * FROM ShortlistCandidate WHERE candidateId = :candidateId")
    fun getCandidateById(candidateId: Int): List<ShortlistCandidateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg shortlistCandidateList: ShortlistCandidateEntity)

    @Query("DELETE FROM ShortlistCandidate")
    fun deleteAll()

    @Query("DELETE FROM ShortlistCandidate WHERE pClientId = :clientId")
    fun deleteByClientId(clientId: Int)
}