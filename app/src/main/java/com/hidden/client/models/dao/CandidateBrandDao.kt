package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.Candidate
import com.hidden.client.models.CandidateBrand

@Dao
interface CandidateBrandDao {

    @get:Query("SELECT * FROM CandidateBrand")
    val all: List<CandidateBrand>

    @Query("SELECT * FROM CandidateBrand WHERE pCandidateId = :candidateId")
    fun getBrandByCandidateId(candidateId: Int): List<CandidateBrand>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg candidateBrands: CandidateBrand)

    @Query("DELETE FROM CandidateBrand")
    fun deleteAll()

    @Query("DELETE FROM CandidateBrand WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}