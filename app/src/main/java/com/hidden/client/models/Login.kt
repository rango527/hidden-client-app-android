package com.hidden.client.models

import androidx.annotation.Nullable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Login(
    @Json(name = "client_id") val clientId: Int,
    @Json(name = "is_admin") val isAdmin: Boolean,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "status") val status: String,
    @Json(name = "token") val token: String,
    var test: String
) {

}