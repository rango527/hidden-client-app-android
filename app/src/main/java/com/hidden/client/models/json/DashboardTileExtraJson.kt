package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DashboardTileExtraJson(

    @Json(name = "job_id")
    val jobId: Int?
)