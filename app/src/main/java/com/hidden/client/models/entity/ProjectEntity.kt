package com.hidden.client.models

import androidx.room.Entity
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
)