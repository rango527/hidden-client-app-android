package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "ShortlistReviewer")
data class ShortlistReviewerEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    var feedbackId: Int?,

    var clientId: Int?,

    var clientAvatar: String?,

    var fullName: String?,

    var isFeedbackSubmitted: Boolean?,

    var vote: String?,

    var comment: String?,

    var pProcessId: Int?
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