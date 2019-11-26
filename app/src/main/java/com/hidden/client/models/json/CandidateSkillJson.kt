package com.hidden.client.models.json

import com.hidden.client.helpers.nullable.NullToZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CandidateSkillJson (

    @Json(name = "skill__skill_id")
    val skillId: Int,

    @Json(name = "skill__name")
    val skillName: String,

    @Json(name = "candidate_skill__ranking")
    val skillRanking: Int,

    @NullToZero
    var pCandidateId: Int = 0
) {

}