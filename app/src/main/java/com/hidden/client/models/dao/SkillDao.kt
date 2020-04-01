package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.SkillEntity

@Dao
interface SkillDao {

    @get:Query("SELECT * FROM CandidateSkill")
    val all: List<SkillEntity>

    @Query("SELECT * FROM CandidateSkill WHERE pCandidateId = :candidateId")
    fun getSkillByCandidateId(candidateId: Int): List<SkillEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg skills: SkillEntity)

    @Query("DELETE FROM CandidateSkill")
    fun deleteAll()

    @Query("DELETE FROM CandidateSkill WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}