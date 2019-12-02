package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.FeedbackEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedbackJson(

    @Json(name = "feedback__feedback_id")
    val id: Int?,

    @Json(name = "feedback__questions")
    val question: String?,

    @Json(name = "feedback__outcome")
    val outcome: String?,

    @Json(name = "feedback__comment")
    val comment: String?,

    @Json(name = "feedback__from")
    val from: String?,

    @Json(name = "feedback_type")
    val type: String?,

    @Json(name = "feedback__translated")
    val translated: String?
) {
    fun toEntity(pCandidateId: Int): FeedbackEntity {
        return FeedbackEntity(
            id.safeValue(),
            question.safeValue(),
            outcome.safeValue(),
            comment.safeValue(),
            from.safeValue(),
            type.safeValue(),
            translated.safeValue(),
            pCandidateId
        )
    }
}