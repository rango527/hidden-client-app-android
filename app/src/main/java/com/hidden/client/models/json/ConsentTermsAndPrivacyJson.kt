package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ConsentTermsAndPrivacyEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConsentTermsAndPrivacyJson(
    @Json(name = "content")
    val content: String?,

    @Json(name = "summary")
    val summary: String?,

    @Json(name = "version")
    val version: Int?
)
{
    fun toEntity(): ConsentTermsAndPrivacyEntity {
        return ConsentTermsAndPrivacyEntity(
            0,
            content.safeValue(),
            summary.safeValue(),
            version.safeValue()
        )
    }
}