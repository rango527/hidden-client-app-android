package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.CandidateSkillEntity

@Dao
interface CandidateSkillDao {

    @get:Query("SELECT * FROM CandidateSkill")
    val all: List<CandidateSkillEntity>

    @Query("SELECT * FROM CandidateSkill WHERE pCandidateId = :candidateId")
    fun getSkillByCandidateId(candidateId: Int): List<CandidateSkillEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg candidateSkills: CandidateSkillEntity)

    @Query("DELETE FROM CandidateSkill")
    fun deleteAll()

    @Query("DELETE FROM CandidateSkill WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}