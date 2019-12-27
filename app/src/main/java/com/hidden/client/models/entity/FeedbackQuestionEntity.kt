package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FeedbackQuestion")
data class FeedbackQuestionEntity (

    @field:PrimaryKey
    val id: Int,

    val question: String,

    val score: Int,

    var pFeedbackId: Int
)