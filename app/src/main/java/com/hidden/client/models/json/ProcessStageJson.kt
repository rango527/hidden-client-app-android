package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ProcessStageEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProcessStageJson(

    @Json(name = "process_status__process_status_id")
    val statusId: Int?,

    @Json(name = "process_status__name")
    val statusName: String?,

    @Json(name = "process_stage__status")
    val stageStatus: String?,

    @Json(name = "process_stage__created_at")
    val stageCreatedAt: String?,

    @Json(name = "client__tile")
    val clientTile: ProcessStageClientTile?,

    @Json(name = "interview__agreed_date")
    val interviewAgreeDate: String?,

    @Json(name = "client_feedback_submitted")
    val clientFeedbackSubmitted: Boolean?,

    @Json(name = "candidate_feedback_submitted")
    val candidateFeedbackSubmitted: Boolean?,

    @Json(name = "process_stage__interview_date_action_required")
    val interviewDateActionRequired: ProcessStageActionRequiredJson?,

    @Json(name = "process_stage__feedback_action_required")
    val feedbackActionRequired: ProcessStageActionRequiredJson?,

    @Json(name = "process__next_stages")
    val nextStages: List<String>?
) {

    fun toEntity(pProcessId: Int): ProcessStageEntity {
        val processStage = ProcessStageEntity(
            statusId.safeValue(),
            statusName.safeValue(),
            stageStatus.safeValue(),
            stageCreatedAt.safeValue(),
            if (clientTile != null) clientTile.backgroundColor.safeValue() else "",
            if (clientTile != null) clientTile.title.safeValue() else "",
            if (clientTile != null) clientTile.icon.safeValue() else "",
            if (clientTile != null) clientTile.text.safeValue() else "",
            if (clientTile != null) clientTile.button.safeValue() else "",
            interviewAgreeDate.safeValue(),
            clientFeedbackSubmitted.safeValue(),
            candidateFeedbackSubmitted.safeValue(),
            if (interviewDateActionRequired != null) interviewDateActionRequired.talentPartner.safeValue() else false,
            if (interviewDateActionRequired != null) interviewDateActionRequired.client.safeValue() else false,
            if (interviewDateActionRequired != null) interviewDateActionRequired.candidate.safeValue() else false,
            if (feedbackActionRequired != null) feedbackActionRequired.talentPartner.safeValue() else false,
            if (feedbackActionRequired != null) feedbackActionRequired.client.safeValue() else false,
            if (feedbackActionRequired != null) feedbackActionRequired.candidate.safeValue() else false,
            "",
            pProcessId)

        if (this.nextStages == null) {
            processStage.setNextStage(listOf())
        } else {
            processStage.setNextStage(this.nextStages)
        }

        return processStage
    }
}