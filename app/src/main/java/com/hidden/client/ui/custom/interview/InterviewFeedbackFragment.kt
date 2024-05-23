package com.hidden.client.ui.custom.interview

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDate
import com.hidden.client.helpers.Utility
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.InterviewEntity
import com.hidden.client.ui.adapters.InterviewerViewPagerAdapter
import com.hidden.client.ui.viewmodels.main.InterviewerViewVM
import com.hidden.horizontalswipelayout.HorizontalSwipeRefreshLayout
import com.viewpagerindicator.CirclePageIndicator
import java.util.*

class InterviewFeedbackFragment (
    private val mContext: Context,
    private val interview: InterviewEntity
) : Fragment() {

    // Viewpager for Shortlist Feedback Sliding
    private lateinit var viewPagerInterviewer: ViewPager
    private lateinit var pageAdapter: InterviewerViewPagerAdapter

    private lateinit var layoutViewPager: LinearLayout

    // Swipe Container
    private lateinit var swipeContainer: HorizontalSwipeRefreshLayout

    // View Pager Indicator
    private lateinit var indicator: CirclePageIndicator

    private var interviewersViewVM: List<InterviewerViewVM> = listOf()

    private lateinit var waitingFeedbackTextView: TextView

    private lateinit var detailLayout: LinearLayout

    private lateinit var btnCandidateFeedbackStatus: Button

    private lateinit var waitingTextView: TextView
    private lateinit var candidateFeedbackTextView: TextView

    private lateinit var averageLayout: LinearLayout
    private lateinit var stageTextView: TextView
    private lateinit var averageTextView: TextView

    private lateinit var btnInterviewerFeedbackStatus: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_interview_feedback, container, false)

        initUI(view)

        setUI()

        return view
    }

    private fun initUI(view: View) {

        waitingFeedbackTextView = view.findViewById(R.id.interview_feedback_waiting_feedback_sentence)

        detailLayout = view.findViewById(R.id.interview_feedback_detail_layout)

        btnCandidateFeedbackStatus = view.findViewById(R.id.interview_info_candidate_feedback_status)

        waitingTextView = view.findViewById(R.id.interview_feedback_waiting_feedback)
        candidateFeedbackTextView = view.findViewById(R.id.interview_feedback_candidate_feedback)

        averageLayout = view.findViewById(R.id.interview_feedback_average_layout)
        stageTextView = view.findViewById(R.id.text_stage_icon)
        averageTextView = view.findViewById(R.id.interview_feedback_average)

        btnInterviewerFeedbackStatus = view.findViewById(R.id.interview_info_interviewer_feedback_status)

        layoutViewPager = view.findViewById(R.id.layout_has_shortlists)

        swipeContainer = view.findViewById(R.id.swipe_container)

        // View Pager
        viewPagerInterviewer = view.findViewById(R.id.viewpager_interviewer)
        indicator = view.findViewById(R.id.indicator)

    }

    private fun setUI() {

        val interviewDate: Date? = HCDate.convertUTCDateStringToLocal(interview.dateTime.safeValue(), null)

        if (interviewDate == null || (System.currentTimeMillis() < interviewDate.time)) {

            waitingFeedbackTextView.visibility = View.VISIBLE
            detailLayout.visibility = View.GONE
        }
        else {

            waitingFeedbackTextView.visibility = View.GONE
            detailLayout.visibility = View.VISIBLE

            // candidate feedback
            when {
                interview.candidateFeedbackDecision.safeValue() == Enums.FeedbackDecisionType.PROGRESS.value -> {
                    btnCandidateFeedbackStatus.text = mContext.getString(R.string.progressing)
                    btnCandidateFeedbackStatus.setBackgroundResource(R.drawable.button_round_green_4)
                }
                interview.candidateFeedbackDecision.safeValue() == Enums.FeedbackDecisionType.REJECT.value -> {
                    btnCandidateFeedbackStatus.text = mContext.getString(R.string.rejected)
                    btnCandidateFeedbackStatus.setBackgroundResource(R.drawable.button_round_red_4)
                }
                else -> {
                    btnCandidateFeedbackStatus.text = mContext.getString(R.string.decision_pending)
                    btnCandidateFeedbackStatus.setBackgroundResource(R.drawable.button_round_dark_gray_4)
                }
            }

            //
            if (interview.candidateFeedbackAverage == null && interview.feedbackTranslation.isNullOrEmpty()) {

                waitingTextView.visibility = View.VISIBLE
                candidateFeedbackTextView.visibility = View.GONE
                averageLayout.visibility = View.GONE
            }
            else {

                waitingTextView.visibility = View.GONE

                if (interview.feedbackTranslation.isNullOrEmpty()) {
                    candidateFeedbackTextView.visibility = View.GONE
                }
                else {
                    candidateFeedbackTextView.visibility = View.VISIBLE
                    candidateFeedbackTextView.text = interview.feedbackTranslation
                }

                if (interview.candidateFeedbackAverage == null) {
                    averageLayout.visibility = View.GONE
                }
                else {
                    averageLayout.visibility = View.VISIBLE

                    // draw star icon
                    val fontType = Typeface.createFromAsset(context!!.assets,"fonts/fontawesome_solid_pro.otf")

                    stageTextView.typeface = fontType
                    stageTextView.text = Utility.getStageClientTileIcon("f005")
                    stageTextView.setTextColor(Color.parseColor("#FF7A32"))

                    // average text
                    averageTextView.text = String.format("%.1f", interview.candidateFeedbackAverage) + " out of 5"
                }
            }

            // interviewer feedback
            when {
                interview.interviewerFeedbackDecision.safeValue() == Enums.FeedbackDecisionType.PROGRESS.value -> {
                    btnInterviewerFeedbackStatus.text = mContext.getString(R.string.progressing)
                    btnInterviewerFeedbackStatus.setBackgroundResource(R.drawable.button_round_green_4)
                }
                interview.interviewerFeedbackDecision.safeValue() == Enums.FeedbackDecisionType.REJECT.value -> {
                    btnInterviewerFeedbackStatus.text = mContext.getString(R.string.rejected)
                    btnInterviewerFeedbackStatus.setBackgroundResource(R.drawable.button_round_red_4)
                }
                else -> {
                    btnInterviewerFeedbackStatus.text = mContext.getString(R.string.decision_pending)
                    btnInterviewerFeedbackStatus.setBackgroundResource(R.drawable.button_round_dark_gray_4)
                }
            }

            // interviewer tiles
            initViewPager()
        }

    }

    private fun initViewPager() {

        val interviewersViewVM: ArrayList<InterviewerViewVM> = arrayListOf()
        for (interviewer in interview.getInterviewerList()) {
            interviewersViewVM.add(InterviewerViewVM(mContext, interviewer))
        }
        this.interviewersViewVM = interviewersViewVM

        viewPagerInterviewer.pageMargin = 20

        pageAdapter = InterviewerViewPagerAdapter(
            mContext,
            interviewersViewVM
        )

        viewPagerInterviewer.adapter = pageAdapter

        // Indicator Init
        indicator.setViewPager(viewPagerInterviewer)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.radius = 5 * density
    }
}