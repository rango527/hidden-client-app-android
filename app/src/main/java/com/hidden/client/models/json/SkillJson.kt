package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.SkillEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SkillJson(

    @Json(name = "skill__skill_id")
    val id: Int?,

    @Json(name = "skill__name")
    val name: String?,

    @Json(name = "candidate_skill__ranking")
    val ranking: Int?
) {
    fun toEntity(pCandidateId: Int): SkillEntity {
        return SkillEntity(
            id.safeValue(),
            name.safeValue(),
            ranking.safeValue(),
            pCandidateId
        )
    }
}