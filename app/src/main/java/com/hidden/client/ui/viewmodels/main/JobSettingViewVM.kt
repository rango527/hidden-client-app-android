package com.hidden.client.ui.viewmodels.main

import android.content.Context
import android.view.View
import com.hidden.client.R
import com.hidden.client.models.entity.JobSettingEntity
import com.hidden.client.ui.adapters.*
import com.hidden.client.ui.viewmodels.root.RootVM

class JobSettingViewVM(private val jobSetting: JobSettingEntity) : RootVM() {

    /**
     * User Manager List
     */
    val userManagerListAdapter: ReviewerListAdapter = ReviewerListAdapter()

    /**
     * Shortlist Interviewer List
     */
    val shortlistReviewerListVisibility: Int = if (jobSetting.getShortlistReviewerList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    val shortlistReviewerListAdapter: ReviewerListAdapter = ReviewerListAdapter()

    fun getShortlistInterviewerCountText(context: Context): String {
        return context.resources.getQuantityString(
            R.plurals.shortlist_reviewer,
            jobSetting.getShortlistReviewerList().size,
            jobSetting.getShortlistReviewerList().size
        )
    }

    /**
     * Interviewer List
     */
    val interviewerListVisibility: Int = if (jobSetting.getInterviewerList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    val interviewerListAdapter: ReviewerListAdapter = ReviewerListAdapter()

    fun getInterviewerCountText(context: Context): String {
        return context.resources.getQuantityString(
            R.plurals.interviewer,
            jobSetting.getShortlistReviewerList().size,
            jobSetting.getShortlistReviewerList().size
        )
    }

    /**
     * Interviewer Advancer List
     */
    val interviewAdvancerListVisibility: Int = if (jobSetting.getInterviewAdvancerList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    val interviewAdvancerListAdapter: ReviewerListAdapter = ReviewerListAdapter()

    fun getInterviewAdvancerCounterText(context: Context): String {
        return context.resources.getQuantityString(
            R.plurals.interviewer_advancer,
            jobSetting.getShortlistReviewerList().size,
            jobSetting.getShortlistReviewerList().size
        )
    }

    /**
     * Offer Manager List
     */
    val offerManagerListVisibility: Int = if (jobSetting.getOfferManagerList().isEmpty())
        View.GONE
    else
        View.VISIBLE

    val offerManagerListAdapter: ReviewerListAdapter = ReviewerListAdapter()

    fun getOfferManagerCount(context: Context): String {
        return context.resources.getQuantityString(
            R.plurals.offer_manager,
            jobSetting.getShortlistReviewerList().size,
            jobSetting.getShortlistReviewerList().size
        )
    }

    // ----------------------------------------------------------- //

    init {

        userManagerListAdapter.updateReviewerList(jobSetting.getUserManagerList())
        shortlistReviewerListAdapter.updateReviewerList(jobSetting.getShortlistReviewerList())
        interviewerListAdapter.updateReviewerList(jobSetting.getInterviewerList())
        interviewAdvancerListAdapter.updateReviewerList(jobSetting.getInterviewAdvancerList())
        offerManagerListAdapter.updateReviewerList(jobSetting.getOfferManagerList())
    }

    fun getJobSetting(): JobSettingEntity {
        return this.jobSetting
    }
}