package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.ProjectAssetsEntity

@Dao
interface ProjectAssetsDao {

    @get:Query("SELECT * FROM CandidateProjectAssets")
    val all: List<ProjectAssetsEntity>

    @Query("SELECT * FROM CandidateProjectAssets WHERE pProjectId = :pProjectId")
    fun getProjectAssetsByProjectId(pProjectId: Int): List<ProjectAssetsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg projectAssets: ProjectAssetsEntity)

    @Query("DELETE FROM CandidateProjectAssets")
    fun deleteAll()

    @Query("DELETE FROM CandidateProjectAssets WHERE pProjectId = :pProjectId")
    fun deleteByProjectId(pProjectId: Int)
}