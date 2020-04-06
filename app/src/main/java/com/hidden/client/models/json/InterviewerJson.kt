package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.FeedbackQuestionEntity
import com.hidden.client.models.entity.InterviewerEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InterviewerJson(
    @Json(name = "feedback__feedback_id")
    val feedbackId: Int?,

    @Json(name = "feedback__comment")
    val comment: String?,

    @Json(name = "client__client_id")
    val clientId: Int?,

    @Json(name = "client__full_name")
    val fullName: String?,

    @Json(name = "asset_client__cloudinary_url")
    val clientAvatar: String?,

    @Json(name = "feedback__is_submitted")
    val isSubmitted: Boolean?,

    @Json(name = "interview__availability_submitted")
    val interviewAvailabilitySubmitted: Boolean?,

    @Json(name = "client__is_current_user")
    val isCurrentUser: Boolean?,

    @Json(name = "feedback__questions")
    val questions: List<FeedbackQuestionJson>?
) {
    fun toEntity(pProcessId: Int, pInterviewId: Int): InterviewerEntity {
        return InterviewerEntity(
            0,
            feedbackId.safeValue(),
            comment.safeValue(),
            clientId.safeValue(),
            fullName.safeValue(),
            clientAvatar.safeValue(),
            isSubmitted,
            interviewAvailabilitySubmitted,
            isCurrentUser,
            pProcessId,
            pInterviewId
        )
    }

    fun toQuestionList(): List<FeedbackQuestionEntity> {
        val list: ArrayList<FeedbackQuestionEntity> = arrayListOf()

        if (questions != null) {
            for (question in questions) {

                var feedbackQuestion = question.toEntity(feedbackId)

                list.add(feedbackQuestion)
            }
        }
        return list
    }
}