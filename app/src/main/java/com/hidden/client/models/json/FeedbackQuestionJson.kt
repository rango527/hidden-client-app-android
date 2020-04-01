package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.FeedbackQuestionEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedbackQuestionJson(

    @Json(name = "feedback_answer__feedback_answer_id")
    val id: Int?,

    @Json(name = "feedback_question__question")
    val question: String?,

    @Json(name = "feedback_answer__score")
    val score: Int?
) {
    fun toEntity(pFeedbackId: Int?): FeedbackQuestionEntity {
        return FeedbackQuestionEntity(
            id.safeValue(),
            question.safeValue(),
            score.safeValue(),
            pFeedbackId.safeValue()
        )
    }
}