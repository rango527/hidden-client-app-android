package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Process")
data class ProcessEntity (

    @field:PrimaryKey
    val id: Int,

    val companyName: String,

    val isTalentPartnerArchived: Boolean,

    val isClientArchived: Boolean,

    val isCandidateArchived: Boolean,

    val statusId: Int,

    val stageId: Int,

    val candidateId: Int,

    val candidateFullName: String,

    val clientFullName: String,

    val candidateAvatar: String,

    val clientAvatar: String,

    val jobId: Int,

    val jobTitle: String,

    val candidateCityName: String,

    val isRejected: Boolean,

    val rejectedBy: String,

    val rejectedOn: String,

//    val statusAfterVote: String,

    val currentInterviewId: String,

    val companyLogo: String,

    val jobCityName: String,

    val isClientSubmissionReviewer: Boolean,

    val isClientInterviewer: Boolean,

    val isClientInterviewerAdvancer: Boolean,

    val isClientOfferManager: Boolean,

    val isClientMessenger: Boolean,

    val feedbackRequired: Boolean,

    val conversationId: Int,

    val messageUnreadNumber: Int,

    val lastMessageCreatedAt: String,

    val myId: Int
) {
    @Ignore
    private var stageList: List<ProcessStageEntity> = listOf()

    fun getStageList(): List<ProcessStageEntity> {
        return stageList
    }

    fun setStageList(stageList: List<ProcessStageEntity>) {
        this.stageList = stageList
    }
}
