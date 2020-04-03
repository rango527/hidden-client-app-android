package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "ShortlistFeedback")
data class ShortlistFeedbackEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    var submitted: String?,

    var submittedAvailability: String?,

    var type: String?,

    var stageId: Int?,

    var description: String?,

    var notes: String?,

    var acceptedAt: String?,

    var voteOutcome: String?,

    var submittedAt: String?,

    var pProcessId: Int?
) {
    @Ignore
    private var shortlistReviewerList: List<ShortlistReviewerEntity> = listOf()

    fun getShortlistReviewerList(): List<ShortlistReviewerEntity> {
        return shortlistReviewerList
    }

    fun setShortlistReviewerList(shortlistReviewerList: List<ShortlistReviewerEntity>) {
        this.shortlistReviewerList = shortlistReviewerList
    }

}
