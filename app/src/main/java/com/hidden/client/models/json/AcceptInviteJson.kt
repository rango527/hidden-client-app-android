package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AcceptInviteJson(

    @Json(name = "client_id")
    val clientId: Int?,

    @Json(name = "full_name")
    val fullName: String?,

    @Json(name = "token")
    val token: String?
)