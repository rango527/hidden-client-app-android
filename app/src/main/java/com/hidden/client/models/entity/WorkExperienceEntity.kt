package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "CandidateWorkExperience")
data class WorkExperienceEntity (

    @field:PrimaryKey
    var id: Int,

    var jobTitle: String,

    var description: String,

    var workedFrom: String,

    val workTo: String,

    val brandName: String,

    val brandImage: String,

    var pCandidateId: Int

)