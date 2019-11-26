package com.hidden.client.models.json

import com.hidden.client.helpers.nullable.NullToZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CandidateProjectJson (

    @Json(name = "project__project_id")
    val projectId: Int,

    @Json(name = "project__title")
    val projectTitle: String,

    @Json(name = "project__brief")
    val projectBrief: String,

    @Json(name = "project__activity")
    val projectActivity: String,

    @Json(name = "brand__name")
    val brandName: String,

    @Json(name = "brand_logo__cloudinary_url")
    val brandImage: String,

    @NullToZero
    var pCandidateId: Int = 0
) {

}