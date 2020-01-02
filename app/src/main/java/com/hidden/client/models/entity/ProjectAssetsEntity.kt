package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CandidateProjectAssets")
data class ProjectAssetsEntity (

    @field:PrimaryKey
    var id: Int,

    var type: String,

    var url: String,

    var mainImage: Boolean,

    var pProjectId: Int
)