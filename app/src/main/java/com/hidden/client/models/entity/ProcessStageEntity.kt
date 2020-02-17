package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProcessStage")
data class ProcessStageEntity (

    @field:PrimaryKey
    val statusId: Int,

    val statusName: String,

    val stageStatus: String,

    val stageCreatedAt: String,

    val interviewAgreeDate: String,

    val clientFeedbackSubmitted: Boolean,

    val candidateFeedbackSubmitted: Boolean,

    val talentPartnerRequired: Boolean,

    val candidateRequired: Boolean,

    val clientRequired: Boolean,

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
