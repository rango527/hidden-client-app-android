package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "CandidateProject")
data class ProjectEntity (

    @field:PrimaryKey
    var id: Int,

    var title: String,

    var brief: String,

    var activity: String,

    var name: String,

    var image: String,

    var pCandidateId: Int
) {
    @Ignore
    private var assetsList: List<ProjectAssetsEntity> = listOf()

    fun getAssetsList(): List<ProjectAssetsEntity> {
        return assetsList
    }

    fun setAssetsList(assetsList: List<ProjectAssetsEntity>) {
        this.assetsList = assetsList
    }
}