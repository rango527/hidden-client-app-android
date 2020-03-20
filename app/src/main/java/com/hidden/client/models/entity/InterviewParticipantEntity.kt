package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "InterviewParticipant")
data class InterviewParticipantEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    val feedbackId: Int?,

    val clientId: Int?,

    val interviewId: Int?,

    val fullName: String?,

    val clientAvatar: String?,

    val isFeedbackSubmitted: String?,

    val interviewAvailabilitySubmitted: String?,

    val isCurrentUser: Int?,

    var pTimelineId: Int?
)
