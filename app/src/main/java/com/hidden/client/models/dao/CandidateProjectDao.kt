package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.CandidateProjectEntity

@Dao
interface CandidateProjectDao {

    @get:Query("SELECT * FROM CandidateProject")
    val all: List<CandidateProjectEntity>

    @Query("SELECT * FROM CandidateProject WHERE pCandidateId = :candidateId")
    fun getProjectByCandidateId(candidateId: Int): List<CandidateProjectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg candidateProjects: CandidateProjectEntity)

    @Query("DELETE FROM CandidateProject")
    fun deleteAll()

    @Query("DELETE FROM CandidateProject WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}