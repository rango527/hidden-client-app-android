package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShortlistJson (

    @Json(name = "client__client_id")
    val clientId: Int,

    @Json(name = "client__full_name")
    val clientFullName: String,

    @Json(name = "asset_client__cloudinary_url")
    val clientUrl: String,

    @Json(name = "candidates")
    val candidates: List<ShortlistJson>
)