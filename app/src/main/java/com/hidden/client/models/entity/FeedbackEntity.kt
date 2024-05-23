package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Feedback")
data class FeedbackEntity (

    @field:PrimaryKey
    val id: Int,

    val clientId: Int,

    val outcome: String,

    val comment: String,

    val from: String,

    val type: String,

    val translated: String,

    var pCandidateId: Int
) {
    @Ignore
    private var questionList: List<FeedbackQuestionEntity> = listOf()

    fun getQuestionList(): List<FeedbackQuestionEntity> {
        return questionList
    }

    fun setQuestionList(questionList: List<FeedbackQuestionEntity>) {
        this.questionList = questionList
    }
}