package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ReviewerEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewerJson(

    @Json(name = "client__client_id")
    val id: Int?,

    @Json(name = "client__avatar")
    val avatar: String?,

    @Json(name = "client__full_name")
    val fullName: String?
) {
    fun toEntity(reviewerType: Int, jobId: Int, myId: Int): ReviewerEntity {
        return ReviewerEntity(
            0,
            id.safeValue(),
            avatar.safeValue(),
            fullName.safeValue(),
            reviewerType,
            jobId,
            myId
        )
    }
}