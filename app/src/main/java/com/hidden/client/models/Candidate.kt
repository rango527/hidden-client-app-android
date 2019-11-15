package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Candidate (

    @field:PrimaryKey
    @Json(name = "candidate__candidate_id")
    val candidateId: Int,

    @Json(name = "asset_candidate__cloudinary_url") val candidatePhoto: String,
    @Json(name = "candidate__first_name") val candidateFirstName: String,
    @Json(name = "candidate__surname") val candidateLastName: String,
    @Json(name = "candidate__full_name") val candidateFullName: String
)
