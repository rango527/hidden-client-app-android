package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.BrandEntity

@Dao
interface BrandDao {

    @get:Query("SELECT * FROM CandidateBrand")
    val all: List<BrandEntity>

    @Query("SELECT * FROM CandidateBrand WHERE pCandidateId = :candidateId")
    fun getBrandByCandidateId(candidateId: Int): List<BrandEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg brands: BrandEntity)

    @Query("DELETE FROM CandidateBrand")
    fun deleteAll()

    @Query("DELETE FROM CandidateBrand WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}