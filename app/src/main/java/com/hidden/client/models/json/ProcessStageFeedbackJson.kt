package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProcessStageFeedbackJson(

    @Json(name = "client__advance_decision_required")
    val advanceRequired: Boolean?
)