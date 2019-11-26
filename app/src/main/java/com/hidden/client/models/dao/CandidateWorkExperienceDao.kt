package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.CandidateWorkExperienceEntity

@Dao
interface CandidateWorkExperienceDao {

    @get:Query("SELECT * FROM CandidateWorkExperience")
    val all: List<CandidateWorkExperienceEntity>

    @Query("SELECT * FROM CandidateWorkExperience WHERE pCandidateId = :candidateId")
    fun getExperienceByCandidateId(candidateId: Int): List<CandidateWorkExperienceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg candidateWorkExperiences: CandidateWorkExperienceEntity)

    @Query("DELETE FROM CandidateWorkExperience")
    fun deleteAll()

    @Query("DELETE FROM CandidateWorkExperience WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}