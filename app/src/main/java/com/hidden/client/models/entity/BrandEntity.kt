package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CandidateBrand")
data class BrandEntity (

    @field:PrimaryKey
    var id: Int,

    var assetId: Int,

    var name: String,

    var assetImage: String,

    var pCandidateId: Int
)
