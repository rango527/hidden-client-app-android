package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProcessStageClientTile (

    @Json(name = "background_colour")
    val backgroundColor: String?,

    @Json(name = "title")
    val title: String?,

    @Json(name = "icon")
    val icon: String?,

    @Json(name = "text")
    val text: String?,

    @Json(name = "button")
    val button: String?

)