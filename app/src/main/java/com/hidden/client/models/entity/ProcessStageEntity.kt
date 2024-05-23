package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProcessStage")
data class ProcessStageEntity (

    @field:PrimaryKey(autoGenerate = true)
    val id: Int,

    val statusId: Int,

    val statusName: String,

    val stageStatus: String,

    val stageCreatedAt: String,

    val clientTileBackgroundColor: String,

    val clientTileTitle: String,

    val clientTileIcon: String,

    val clientTileIconColor: String,

    val clientTileIconStyle: String,

    val clientTileIconCode: String,

    val clientTileText: String,

    val clientTileButton: String,

    val interviewAgreeDate: String,

    val clientFeedbackSubmitted: Boolean,

    val candidateFeedbackSubmitted: Boolean,

    val interviewDateTalentPartnerRequired: Boolean,

    val interviewDateCandidateRequired: Boolean,

    val interviewDateClientRequired: Boolean,

    val feedbackTalentPartnerRequired: Boolean,

    val FeedbackDateCandidateRequired: Boolean,

    val feedbackDateClientRequired: Boolean,

    var nextStages: String,

    var pProcessId: Int
) {

    fun getNextStageList(): List<String> {
        return nextStages.split(",")
    }

    fun setNextStage(nextStageList: List<String>) {
        this.nextStages = nextStageList.joinToString()
    }
}
