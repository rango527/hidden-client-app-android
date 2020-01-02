package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CandidateSkill")
data class SkillEntity (

    @field:PrimaryKey
    var id: Int,

    var name: String,

    var ranking: Int,

    var pCandidateId: Int
)