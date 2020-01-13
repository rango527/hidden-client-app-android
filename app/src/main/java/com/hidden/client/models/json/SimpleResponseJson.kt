package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SimpleResponseJson(

    @Json(name = "errors")
    val errors: List<String>,

    @Json(name = "stat")
    val stat: Int
)