package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hidden.client.helpers.nullable.NullToZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class CandidateSkill (

    @field:PrimaryKey
    @Json(name = "skill__skill_id")
    val skillId: Int,

    @Json(name = "skill__name")
    val skillName: String,

    @Json(name = "candidate_skill__ranking")
    val skillRanking: Int,

    @NullToZero
    var pCandidateId: Int = 0
)