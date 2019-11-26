package com.hidden.client.models.json

import com.hidden.client.helpers.nullable.NullToEmptyString
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CandidateJson (

    @Json(name = "candidate__candidate_id")
    val candidateId: Int,

    @Json(name = "asset_candidate__cloudinary_url")
    @NullToEmptyString
    var candidatePhoto: String? = "",

    @Json(name = "candidate__first_name")
    val candidateFirstName: String,

    @Json(name = "candidate__surname")
    val candidateLastName: String,

    @Json(name = "candidate__full_name")
    val candidateFullName: String
) {

}
