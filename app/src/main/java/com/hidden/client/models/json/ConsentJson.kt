package com.hidden.client.models.json

import com.hidden.client.helpers.AppPreferences.myId
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ConsentEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConsentJson(

    @Json(name = "type")
    val type: String?,

    @Json(name = "accepted_version")
    val acceptedVersion: String?,

    @Json(name = "new_version")
    val newVersion: String?
) {
    fun toEntity(myId: Int): ConsentEntity {
        return ConsentEntity(
            0,
            type.safeValue(),
            acceptedVersion.safeValue(),
            newVersion.safeValue(),
            myId
        )
    }
}