package com.hidden.client.models.json

import com.hidden.client.models.entity.FeedbackIDEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedbackIDsJson(

    @Json(name = "feedback__feedback_id")
    val id: Int?,

    @Json(name = "client__client_id")
    val clientId: Int?,

    @Json(name = "asset_client__cloudinary_url")
    val clientAvatar: String?,

    @Json(name = "client__full_name")
    val clientFullName: String?,

    @Json(name = "feedback__is_submitted")
    val feedbackSubmitted: Boolean?
) {
    fun toEntity(pTimelineId: Int, pProcessId: Int): FeedbackIDEntity {
        return FeedbackIDEntity(
            0,
            id,
            clientId,
            clientAvatar,
            clientFullName,
            feedbackSubmitted,
            pTimelineId,
            pProcessId
        )
    }
}