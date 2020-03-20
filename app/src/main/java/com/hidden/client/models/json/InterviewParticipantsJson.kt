package com.hidden.client.models.json

import com.hidden.client.models.entity.InterviewParticipantEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InterviewParticipantsJson(

    @Json(name = "feedback__feedback_id")
    val feedbackId: Int?,

    @Json(name = "client__client_id")
    val clientId: Int?,

    @Json(name = "interview__interview_id")
    val interviewId: Int?,

    @Json(name = "client__full_name")
    val fullName: String?,

    @Json(name = "asset_client__cloudinary_url")
    val clientAvatar: String?,

    @Json(name = "feedback__is_submitted")
    val isFeedbackSubmitted: String?,

    @Json(name = "interview__availability_submitted")
    val interviewAvailabilitySubmitted: String?,

    @Json(name = "client__is_current_user")
    val isCurrentUser: Int?
) {
    fun toEntity(pTimelineId: Int): InterviewParticipantEntity {
        return InterviewParticipantEntity(
            0,
            feedbackId,
            clientId,
            interviewId,
            fullName,
            clientAvatar,
            isFeedbackSubmitted,
            interviewAvailabilitySubmitted,
            isCurrentUser,
            pTimelineId
        )
    }
}