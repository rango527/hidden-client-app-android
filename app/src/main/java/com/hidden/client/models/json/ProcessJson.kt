package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.models.entity.ProcessStageEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProcessJson(

    @Json(name = "process__process_id")
    val id: Int?,

    @Json(name = "company__name")
    val companyName: String?,

    @Json(name = "process__is_talent_partner_archived")
    val isTalentPartnerArchived: Boolean?,

    @Json(name = "process__is_client_archived")
    val isClientArchived: Boolean?,

    @Json(name = "process__is_candidate_archived")
    val isCandidateArchived: Boolean?,

    @Json(name = "process__process_status_id")
    val statusId: Int?,

    @Json(name = "process_stage__process_stage_id")
    val stageId: Int?,

    @Json(name = "candidate__candidate_id")
    val candidateId: Int?,

    @Json(name = "candidate__full_name")
    val candidateFullName: String?,

    @Json(name = "client__full_name")
    val clientFullName: String?,

    @Json(name = "asset_candidate__cloudinary_url")
    val candidateAvatar: String?,

    @Json(name = "asset_client__cloudinary_url")
    val clientAvatar: String?,

    @Json(name = "job__job_id")
    val jobId: Int?,

    @Json(name = "job__title")
    val jobTitle: String?,

    @Json(name = "candidate_city__name")
    val candidateCityName: String?,

    @Json(name = "process__is_rejected")
    val isRejected: Boolean?,

    @Json(name = "process__rejected_by")
    val rejectedBy: String?,

    @Json(name = "process__rejected_on")
    val rejectedOn: String?,

    @Json(name = "process__current_interview_id")
    val currentInterviewId: String?,

    @Json(name = "asset_company_logo__cloudinary_url")
    val companyLogo: String?,

    @Json(name = "job_city__name")
    val jobCityName: String?,

    @Json(name = "process_client__is_submission_reviewer")
    val isClientSubmissionReviewer: Boolean?,

    @Json(name = "process_client__is_interviewer")
    val isClientInterviewer: Boolean?,

    @Json(name = "process_client__is_interview_advancer")
    val isClientInterviewerAdvancer: Boolean?,

    @Json(name = "process_client__is_offer_manager")
    val isClientOfferManager: Boolean?,

    @Json(name = "process_client__is_messenger")
    val isClientMessenger: Boolean?,

    @Json(name = "process__stages")
    val stages: List<ProcessStageJson>?,

    @Json(name = "conversation__conversation_id")
    val conversationId: Int?,

    @Json(name = "messages__number_unread")
    val messageUnreadNumber: Int?,

    @Json(name = "messages__last_message_created_at")
    val lastMessageCreatedAt: String?
) {

    fun toEntity(myId: Int): ProcessEntity {
        return ProcessEntity(
            id.safeValue(),
            companyName.safeValue(),
            isTalentPartnerArchived.safeValue(),
            isClientArchived.safeValue(),
            isCandidateArchived.safeValue(),
            statusId.safeValue(),
            stageId.safeValue(),
            candidateId.safeValue(),
            candidateFullName.safeValue(),
            clientFullName.safeValue(),
            candidateAvatar.safeValue(),
            clientAvatar.safeValue(),
            jobId.safeValue(),
            jobTitle.safeValue(),
            candidateCityName.safeValue(),
            isRejected.safeValue(),
            rejectedBy.safeValue(),
            rejectedOn.safeValue(),
            currentInterviewId.safeValue(),
            companyLogo.safeValue(),
            jobCityName.safeValue(),
            isClientSubmissionReviewer.safeValue(),
            isClientInterviewer.safeValue(),
            isClientInterviewerAdvancer.safeValue(),
            isClientOfferManager.safeValue(),
            isClientMessenger.safeValue(),
            conversationId.safeValue(),
            messageUnreadNumber.safeValue(),
            lastMessageCreatedAt.safeValue(),
            myId)
    }

    fun toStageEntityList(processId: Int): List<ProcessStageEntity> {
        val stageList: ArrayList<ProcessStageEntity> = arrayListOf()

        for (stage in this.stages!!) {
            stageList.add(stage.toEntity(processId))
        }

        return stageList
    }
}