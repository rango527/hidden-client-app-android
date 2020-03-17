package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimelineJson(

    @Json(name = "description")
    val description: String?,

    @Json(name = "type")
    val type: String?,

    @Json(name = "client_feedback_submitted")
    val clientFeedbackSubmitted: Boolean?,

    @Json(name = "interviewer_feedback_average")
    val interviewerFeedbackAverage: String?,

    @Json(name = "interviewer_feedback_decision")
    val interviewerFeedbackDecision: String?,

    @Json(name = "candidate_feedback_average")
    val candidateFeedbackAverage: String?,

    @Json(name = "candidate_feedback_decision")
    val candidateFeedbackDecision: String?,

    @Json(name = "feedback_candidate__feedback_id")
    val candidateFeedbackId: Int?,

    @Json(name = "interview_id")
    val interviewId: Int?,

    @Json(name = "date_and_time")
    val dateTime: String?,

    @Json(name = "client_submitted_interview_dates")
    val clientSubmittedInterviewDates: Boolean?,

    @Json(name = "candidate_submitted_interview_dates")
    val candidateSubmittedInterviewDates: Boolean?,

    @Json(name = "location")
    val location: String?,

    @Json(name = "client_notes")
    val clientNotes: String?,

    @Json(name = "lat_lng")
    val latLng: String?,

//    @Json(name = "interview_participants")
//    val interviewParticipants: List<String>?

    @Json(name = "accepted_at")
    val acceptedAt: String?,

    @Json(name = "submitted_at")
    val submittedAt: String?,

    @Json(name = "feedback_ids")
    val feedbackIds: List<FeedbackIDsJson>?,

    @Json(name = "feedback_id")
    val feedbackId: Int?
)