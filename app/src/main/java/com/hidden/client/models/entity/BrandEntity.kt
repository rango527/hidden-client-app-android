package com.hidden.client.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hidden.client.models.entity.CandidateEntity

@Entity(tableName = "CandidateBrand")
data class BrandEntity (

    @field:PrimaryKey
    var id: Int,

    var assetId: Int,

    var name: String,

    var assetImage: String,

    @ForeignKey(entity = CandidateEntity::class, parentColumns = ["id"], childColumns = ["pCandidateId"])
    var pCandidateId: Int

)
