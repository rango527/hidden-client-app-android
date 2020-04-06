package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.InterviewEntity
import com.hidden.client.models.entity.InterviewerEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InterviewJson(
    @Json(name = "process_stage_id")
    val stageId: Int?,

    @Json(name = "description")
    val description: String?,

    @Json(name = "candidate__feedback_translation")
    val feedbackTranslation: String?,

    @Json(name = "candidate__first_name")
    val firstName: String?,

    @Json(name = "candidate__full_name")
    val fullName: String?,

    @Json(name = "date_and_time")
    val dateTime: String?,

    @Json(name = "interviewers")
    val interviewers: List<InterviewerJson>?,

    @Json(name = "interviewer_feedback_average")
    val interviewerFeedbackAverage: Double?,

    @Json(name = "interviewer_feedback_decision")
    val interviewerFeedbackDecision: String?,

    @Json(name = "candidate_feedback_average")
    val candidateFeedbackAverage: Double?,

    @Json(name = "candidate_feedback_decision")
    val candidateFeedbackDecision: String?,

    @Json(name = "feedback_candidate__feedback_id")
    val candidateFeedbackId: Int?,

    @Json(name = "interview_id")
    val interviewId: Int?,

    @Json(name = "client_submitted")
    val submitted: String?,

    @Json(name = "candidate_submitted_availability")
    val submittedAvailability: String?,

    @Json(name = "location")
    val location: String?,

    @Json(name = "notes")
    val notes: String?,

    @Json(name = "lat_lng")
    val latLng: String?
) {
    fun toEntity(pProcessId: Int): InterviewEntity {
        return InterviewEntity(
            0,
            stageId.safeValue(),
            description.safeValue(),
            feedbackTranslation.safeValue(),
            firstName.safeValue(),
            fullName.safeValue(),
            dateTime.safeValue(),
            interviewerFeedbackAverage,
            interviewerFeedbackDecision.safeValue(),
            candidateFeedbackAverage,
            candidateFeedbackDecision.safeValue(),
            candidateFeedbackId.safeValue(),
            interviewId.safeValue(),
            submitted.safeValue(),
            submittedAvailability.safeValue(),
            location.safeValue(),
            notes.safeValue(),
            latLng.safeValue(),
            pProcessId
        )
    }

    fun toInterviewerList(pProcessId: Int): List<InterviewerEntity> {
        val list: ArrayList<InterviewerEntity> = arrayListOf()

        if (interviewers != null) {
            for (interviewer in interviewers) {

                var interviewerEntity = interviewer.toEntity(pProcessId, interviewId!!)

                val questions = interviewer.toQuestionList()

                interviewerEntity.setFeedbackQuestionList(questions)

                list.add(interviewerEntity)
            }
        }
        return list
    }
}