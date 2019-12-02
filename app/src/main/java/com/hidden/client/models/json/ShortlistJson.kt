package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.ShortlistCandidateEntity
import com.hidden.client.models.ShortlistEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShortlistJson(

    @Json(name = "client__client_id")
    val clientId: Int?,

    @Json(name = "client__full_name")
    val clientFullName: String?,

    @Json(name = "asset_client__cloudinary_url")
    val clientUrl: String?,

    @Json(name = "candidates")
    val shortlistCandidates: List<ShortlistCandidateJson>?
) {
    fun toEntity(): ShortlistEntity {
        return ShortlistEntity(
            clientId.safeValue(),
            clientFullName.safeValue(),
            clientUrl.safeValue()
        )
    }

    fun toShortlistCandidateEntityList(pShortlistId: Int): List<ShortlistCandidateEntity> {
        var shortlistCandidateEntityList: ArrayList<ShortlistCandidateEntity> = arrayListOf()

        for (shortlistCandidate in shortlistCandidates!!) {
            shortlistCandidateEntityList.add(shortlistCandidate.toEntity(pShortlistId))
        }

        return shortlistCandidateEntityList
    }
}