package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Interview")
data class InterviewEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    val stageId: Int?,

    val description: String?,

    val feedbackTranslation: String?,

    val firstName: String?,

    val fullName: String?,

    val dateTime: String?,

    val interviewerFeedbackAverage: Double?,

    val interviewerFeedbackDecision: String?,

    val candidateFeedbackAverage: Double?,

    val candidateFeedbackDecision: String?,

    val candidateFeedbackId: Int?,

    val interviewId: Int?,

    val submitted: String?,

    val submittedAvailability: String?,

    val location: String?,

    val notes: String?,

    val latLng: String?,

    var pProcessId: Int?
) {
    @Ignore
    private var interviewerList: List<InterviewerEntity> = listOf()

    fun getInterviewerList(): List<InterviewerEntity> {
        return interviewerList
    }

    fun setInterviewerList(interviewerList: List<InterviewerEntity>) {
        this.interviewerList = interviewerList
    }
}
