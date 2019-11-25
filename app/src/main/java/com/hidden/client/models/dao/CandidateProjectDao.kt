package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.CandidateProject

@Dao
interface CandidateProjectDao {

    @get:Query("SELECT * FROM CandidateProject")
    val all: List<CandidateProject>

    @Query("SELECT * FROM CandidateProject WHERE pCandidateId = :candidateId")
    fun getProjectByCandidateId(candidateId: Int): List<CandidateProject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg candidateProjects: CandidateProject)

    @Query("DELETE FROM CandidateProject")
    fun deleteAll()

    @Query("DELETE FROM CandidateProject WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}