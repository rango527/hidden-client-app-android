package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FeedbackID")
data class FeedbackIDEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    val feedbackId: Int?,

    val clientId: Int?,

    val clientAvatar: String?,

    val clientFullName: String?,

    val feedbackSubmitted: Boolean?,

    val pTimelineId: Int?,

    val pProcessId: Int?
)
