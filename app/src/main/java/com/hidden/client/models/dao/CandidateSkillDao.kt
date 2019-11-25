package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.CandidateSkill

@Dao
interface CandidateSkillDao {

    @get:Query("SELECT * FROM CandidateSkill")
    val all: List<CandidateSkill>

    @Query("SELECT * FROM CandidateSkill WHERE pCandidateId = :candidateId")
    fun getSkillByCandidateId(candidateId: Int): List<CandidateSkill>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg candidateSkills: CandidateSkill)

    @Query("DELETE FROM CandidateSkill")
    fun deleteAll()

    @Query("DELETE FROM CandidateSkill WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}