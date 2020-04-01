package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.ProjectEntity

@Dao
interface ProjectDao {

    @get:Query("SELECT * FROM CandidateProject")
    val all: List<ProjectEntity>

    @Query("SELECT * FROM CandidateProject WHERE pCandidateId = :candidateId")
    fun getProjectByCandidateId(candidateId: Int): List<ProjectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg projects: ProjectEntity)

    @Query("DELETE FROM CandidateProject")
    fun deleteAll()

    @Query("DELETE FROM CandidateProject WHERE pCandidateId = :candidateId")
    fun deleteByCandidateId(candidateId: Int)
}