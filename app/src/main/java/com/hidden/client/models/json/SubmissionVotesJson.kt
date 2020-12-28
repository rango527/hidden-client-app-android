package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubmissionVotesJson(
    @Json(name = "current_outcome")
    val statusAfterVote: String?
)