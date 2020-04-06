package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Interviewer")
data class InterviewerEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    val feedbackId: Int?,

    val comment: String?,

    val clientId: Int?,

    val fullName: String?,

    val clientAvatar: String?,

    val isSubmitted: Boolean?,

    val interviewAvailabilitySubmitted: Boolean?,

    val isCurrentUser: Boolean?,

    var pProcessId: Int?,

    var pInterviewId: Int?
) {
    @Ignore
    private var questionsList: List<FeedbackQuestionEntity> = listOf()

    fun getFeedbackQuestionList(): List<FeedbackQuestionEntity> {
        return questionsList
    }

    fun setFeedbackQuestionList(questionsList: List<FeedbackQuestionEntity>) {
        this.questionsList = questionsList
    }
}
