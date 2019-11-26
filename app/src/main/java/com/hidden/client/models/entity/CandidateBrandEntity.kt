package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CandidateBrand")
data class CandidateBrandEntity (

    @field:PrimaryKey
    var brandId: Int,

    var assetId: Int,

    var brandName: String,

    var assetImage: String,

    var pCandidateId: Int

)