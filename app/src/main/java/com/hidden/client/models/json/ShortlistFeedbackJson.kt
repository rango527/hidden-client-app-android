package com.hidden.client.models.json

import android.util.Log
import com.hidden.client.models.entity.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShortlistFeedbackJson(

    @Json(name = "client_submitted")
    val submitted: String?,

    @Json(name = "candidate_submitted_availability")
    val submittedAvailability: String?,

    @Json(name = "type")
    val type: String?,

    @Json(name = "process_stage_id")
    val stageId: Int?,

    @Json(name = "description")
    val description: String?,

    @Json(name = "notes")
    val notes: String?,

    @Json(name = "accepted_at")
    val acceptedAt: String?,

    @Json(name = "shortlist__vote_outcome")
    val voteOutcome: String?,

    @Json(name = "submitted_at")
    val submittedAt: String?,

    @Json(name = "shortlist__reviewers")
    val reviewers: List<ShortlistReviewerJson>?
) {
    fun toEntity(pProcessId: Int): ShortlistFeedbackEntity {
        return ShortlistFeedbackEntity(
            0,
            submitted,
            submittedAvailability,
            type,
            stageId,
            description,
            notes,
            acceptedAt,
            voteOutcome,
            submittedAt,
            pProcessId
        )
    }

    fun toShortlistReviewerList(pProcessId: Int): List<ShortlistReviewerEntity> {
        val list: ArrayList<ShortlistReviewerEntity> = arrayListOf()

        if (reviewers != null) {
            for (reviewer in reviewers) {

                var feedbackReviewer = reviewer.toEntity(pProcessId)

                var pShortlistFeedbackId: Int = 0
                if (feedbackReviewer.feedbackId!=null) pShortlistFeedbackId = feedbackReviewer.feedbackId!!

                val questions = reviewer.toQuestionList(pShortlistFeedbackId, true)

                feedbackReviewer.setQuestionList(questions)

                list.add(feedbackReviewer)
            }
        }
        return list
    }

}