package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.WorkExperienceEntity

@Dao
interface WorkExperienceDao {

    @get:Query("SELECT * FROM CandidateWorkExperience")
    val all: List<WorkExperienceEntity>

    @Query("SELECT * FROM CandidateWorkExperience WHERE pCandidateId = :candidateId")
    fun getExperienceByCandidateId(candidateId: Int): List<WorkExperienceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg workExperiences: WorkExperienceEntity)

    @Query("DELETE FROM CandidateWorkExperience")
    fun deleteAll()

    @Query("DELETE FROM CandidateWorkExperience WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}