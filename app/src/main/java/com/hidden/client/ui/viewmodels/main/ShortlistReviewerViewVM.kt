package com.hidden.client.ui.viewmodels.main

import android.content.Context
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ShortlistReviewerEntity
import com.hidden.client.ui.adapters.*
import com.hidden.client.ui.viewmodels.root.RootVM

class ShortlistReviewerViewVM(private val context: Context, private val shortlistReviewerEntity: ShortlistReviewerEntity) : RootVM() {

    /**
     *      full name
     */
    val fullName: String? = shortlistReviewerEntity.fullName

    /**
     *      avatar
     */
    var avatarImage: String? = shortlistReviewerEntity.clientAvatar

    /**
     *      vote
     */
    val vote: String? = shortlistReviewerEntity.vote

    /**
     *      question
     */
    val feedbackQuestionViewAdapter: FeedbackQuestionListAdapter = FeedbackQuestionListAdapter(context, shortlistReviewerEntity.getQuestionList())

    /**
     *      comment
     */
    val comment: String  = if (shortlistReviewerEntity.comment.isNullOrEmpty())
        "No Comment"
    else
        shortlistReviewerEntity.comment!!

    /**
     *      is empty
     */
    val isEmpty: Boolean = (shortlistReviewerEntity.isFeedbackSubmitted == true)

    /**
     *      vote to button
     */
    fun getVoteStatus(): String {

        when {
            vote.safeValue() == Enums.VoteType.APPROVE.value -> {
                return context.getString(R.string.approve)
            }
            vote.safeValue() == Enums.VoteType.REJECT.value -> {
                return context.getString(R.string.reject)
            }
            else -> {
                return context.getString(R.string.none)
            }
        }

    }

}