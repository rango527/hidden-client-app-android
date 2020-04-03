package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginJson(

    @Json(name = "client_id")
    val clientId: Int?,

    @Json(name = "is_admin")
    val isAdmin: Boolean?,

    @Json(name = "is_user_manager")
    val isUserManager: Boolean?,

    @Json(name = "full_name")
    val fullName: String?,

    @Json(name = "token")
    val token: String?
)