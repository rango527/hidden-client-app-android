package com.hidden.client.models.json

import com.hidden.client.helpers.nullable.NullToZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CandidateWorkExperienceJson (

    @Json(name = "work_experience__work_experience_id")
    val experienceId: Int,

    @Json(name = "work_experience__job_title")
    val jobTitle: String,

    @Json(name = "work_experience__description")
    val description: String,

    @Json(name = "work_experience__worked_from")
    val workedFrom: String,

    @Json(name = "work_experience__worked_to")
    val workTo: String,

    @Json(name = "brand__name")
    val brandName: String,

    @Json(name = "asset__cloudinary_url")
    val brandImage: String,

    @NullToZero
    var pCandidateId: Int = 0
) {

}