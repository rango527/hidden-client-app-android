package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.CandidateWorkExperience

@Dao
interface CandidateWorkExperienceDao {

    @get:Query("SELECT * FROM CandidateWorkExperience")
    val all: List<CandidateWorkExperience>

    @Query("SELECT * FROM CandidateWorkExperience WHERE pCandidateId = :candidateId")
    fun getExperienceByCandidateId(candidateId: Int): List<CandidateWorkExperience>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg candidateWorkExperiences: CandidateWorkExperience)

    @Query("DELETE FROM CandidateWorkExperience")
    fun deleteAll()

    @Query("DELETE FROM CandidateWorkExperience WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}