package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Feedback")
data class FeedbackEntity (

    @field:PrimaryKey
    val id: Int,

    val question: String,

    val outcome: String,

    val comment: String,

    val from: String,

    val type: String,

    val translated: String,

    var pCandidateId: Int
)