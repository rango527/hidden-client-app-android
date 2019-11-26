package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CandidateProject")
data class CandidateProjectEntity (

    @field:PrimaryKey
    var projectId: Int,

    var projectTitle: String,

    var projectBrief: String,

    var projectActivity: String,

    var brandName: String,

    var brandImage: String,

    var pCandidateId: Int
)