package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.CandidateBrandEntity

@Dao
interface CandidateBrandDao {

    @get:Query("SELECT * FROM CandidateBrand")
    val all: List<CandidateBrandEntity>

    @Query("SELECT * FROM CandidateBrand WHERE pCandidateId = :candidateId")
    fun getBrandByCandidateId(candidateId: Int): List<CandidateBrandEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg candidateBrands: CandidateBrandEntity)

    @Query("DELETE FROM CandidateBrand")
    fun deleteAll()

    @Query("DELETE FROM CandidateBrand WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}