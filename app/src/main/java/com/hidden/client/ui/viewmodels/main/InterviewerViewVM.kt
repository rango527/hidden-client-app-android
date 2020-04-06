package com.hidden.client.ui.viewmodels.main

import android.content.Context
import com.hidden.client.models.entity.FeedbackQuestionEntity
import com.hidden.client.models.entity.InterviewerEntity
import com.hidden.client.ui.adapters.*
import com.hidden.client.ui.viewmodels.root.RootVM

class InterviewerViewVM(private val context: Context, private val interviewerEntity: InterviewerEntity) : RootVM() {

    /**
     *      full name
     */
    val fullName: String? = interviewerEntity.fullName

    /**
     *      avatar
     */
    var avatarImage: String? = interviewerEntity.clientAvatar

    /**
     *      question
     */
    val feedbackQuestionViewAdapter: FeedbackQuestionListAdapter = FeedbackQuestionListAdapter(context, interviewerEntity.getFeedbackQuestionList())

    /**
     *      comment
     */
    val comment: String  = if (interviewerEntity.comment.isNullOrEmpty())
        "No Comment"
    else
        interviewerEntity.comment!!

    /**
     *      is empty
     */
    val isEmpty: Boolean = (interviewerEntity.isSubmitted == true)

    /**
     *      is current user
     */
    val isCurrentUser: Boolean = (interviewerEntity.isCurrentUser == true)

}