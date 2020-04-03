package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Timeline")
data class TimelineEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    val description: String?,

    val type: String?,

    val clientFeedbackSubmitted: Boolean?,

    val interviewerFeedbackAverage: String?,

    val interviewerFeedbackDecision: String?,

    val candidateFeedbackAverage: String?,

    val candidateFeedbackDecision: String?,

    val candidateFeedbackId: Int?,

    val interviewId: Int?,

    val dateTime: String?,

    val clientSubmittedInterviewDates: Boolean?,

    val candidateSubmittedInterviewDates: Boolean?,

    val location: String?,

    val clientNotes: String?,

    val latLng: String?,

    val acceptedAt: String?,

    val submittedAt: String?,

    val feedbackId: Int?,

    var pProcessId: Int?
) {
    @Ignore
    private var interviewParticipantList: List<InterviewParticipantEntity> = listOf()

    @Ignore
    private var feedbackIdList: List<FeedbackIDEntity> = listOf()

    fun getInterviewParticipantList(): List<InterviewParticipantEntity> {
        return interviewParticipantList
    }

    fun setInterviewParticipantList(interviewParticipantList: List<InterviewParticipantEntity>) {
        this.interviewParticipantList = interviewParticipantList
    }

    fun getFeedbackIdList(): List<FeedbackIDEntity> {
        return feedbackIdList
    }

    fun setFeedbackIdList(feedbackIdList: List<FeedbackIDEntity>) {
        this.feedbackIdList = feedbackIdList
    }
}
