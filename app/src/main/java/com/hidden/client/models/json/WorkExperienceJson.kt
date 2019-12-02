package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.helpers.nullable.NullToZero
import com.hidden.client.models.WorkExperienceEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WorkExperienceJson(

    @Json(name = "work_experience__work_experience_id")
    val id: Int?,

    @Json(name = "work_experience__job_title")
    val jobTitle: String?,

    @Json(name = "work_experience__description")
    val description: String?,

    @Json(name = "work_experience__worked_from")
    val workedFrom: String?,

    @Json(name = "work_experience__worked_to")
    val workTo: String?,

    @Json(name = "brand__name")
    val brandName: String?,

    @Json(name = "asset__cloudinary_url")
    val brandImage: String?,

    @NullToZero
    var pCandidateId: Int? = 0
) {
    fun toEntity(pCandidateId: Int): WorkExperienceEntity {
        return WorkExperienceEntity(
            id.safeValue(),
            jobTitle.safeValue(),
            description.safeValue(),
            workedFrom.safeValue(),
            workTo.safeValue(),
            brandName.safeValue(),
            brandImage.safeValue(),
            pCandidateId
        )
    }
}