package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CandidateSkill")
data class CandidateSkillEntity (

    @field:PrimaryKey
    var skillId: Int,

    var skillName: String,

    var skillRanking: Int,

    var pCandidateId: Int
)