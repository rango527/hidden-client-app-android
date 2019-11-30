package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Feedback")
data class FeedbackEntity (

    @field:PrimaryKey
    val feedbackId: Int,

    val feedbackQuestion: String,

    val outcome: String,

    val comment: String,

    val from: String,

    val type: String,

    val translated: String,

    var pCandidateId: Int
)