package com.hidden.client.models.json

import android.util.Log
import com.hidden.client.models.entity.FeedbackQuestionEntity
import com.hidden.client.models.entity.InterviewParticipantEntity
import com.hidden.client.models.entity.ShortlistReviewerEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShortlistReviewerJson(

    @Json(name = "feedback__feedback_id")
    val feedbackId: Int?,

    @Json(name = "client__client_id")
    val clientId: Int?,

    @Json(name = "asset_client__cloudinary_url")
    val clientAvatar: String?,

    @Json(name = "client__full_name")
    val fullName: String?,

    @Json(name = "feedback__is_submitted")
    val isFeedbackSubmitted: Boolean?,

    @Json(name = "feedback__vote")
    val vote: String?,

    @Json(name = "feedback__comment")
    val comment: String?,

    @Json(name = "feedback__questions")
    val questions: List<FeedbackQuestionJson>?

) {
    fun toEntity(pProcessId: Int): ShortlistReviewerEntity {
        return ShortlistReviewerEntity(
            0,
            feedbackId,
            clientId,
            clientAvatar,
            fullName,
            isFeedbackSubmitted,
            vote,
            comment,
            pProcessId
        )
    }

    fun toQuestionList(pFeedbackId: Int, resetScore: Boolean): List<FeedbackQuestionEntity> {

        val questionEntityList: ArrayList<FeedbackQuestionEntity> = arrayListOf()

        if (questions != null) {
            for (question in this.questions) {
                val questionEntity = question.toEntity(pFeedbackId)
                if (resetScore) {
                    questionEntity.score = 0
                }
                questionEntityList.add(questionEntity)
            }
        }

        return questionEntityList
    }

}