package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.FeedbackEntity
import com.hidden.client.models.entity.FeedbackQuestionEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedbackJson(

    @Json(name = "feedback__feedback_id")
    val id: Int?,

    @Json(name = "feedback__client_id")
    val clientId: Int?,

    @Json(name = "feedback__outcome")
    val outcome: String?,

    @Json(name = "feedback__comment")
    val comment: String?,

    @Json(name = "feedback__from")
    val from: String?,

    @Json(name = "feedback_type")
    val type: String?,

    @Json(name = "feedback__translated")
    val translated: String?,

    @Json(name = "feedback__questions")
    val questionList: List<FeedbackQuestionJson>?
) {
    fun toEntity(pCandidateId: Int): FeedbackEntity {
        return FeedbackEntity(
            id.safeValue(),
            clientId.safeValue(),
            outcome.safeValue(),
            comment.safeValue(),
            from.safeValue(),
            type.safeValue(),
            translated.safeValue(),
            pCandidateId
        )
    }

    fun toQuestionList(pFeedbackId: Int): List<FeedbackQuestionEntity> {
        val questionEntityList: ArrayList<FeedbackQuestionEntity> = arrayListOf()

        for (question in this.questionList!!) {
            questionEntityList.add(question.toEntity(pFeedbackId))
        }

        return questionEntityList
    }
}