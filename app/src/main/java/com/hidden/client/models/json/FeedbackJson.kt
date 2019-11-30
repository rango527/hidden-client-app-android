package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedbackJson (

    @Json(name = "feedback__feedback_id")
    val feedbackId: Int,

    @Json(name = "feedback__questions")
    val feedbackQuestion: String,

    @Json(name = "feedback__outcome")
    val outcome: String,

    @Json(name = "feedback__comment")
    val comment: String,

    @Json(name = "feedback__from")
    val from: String,

    @Json(name = "feedback_type")
    val type: String,

    @Json(name = "feedback__translated")
    val translated: String
)