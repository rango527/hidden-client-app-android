package com.hidden.client.ui.custom.process

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDate
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.TimelineEntity
import com.hidden.client.ui.activities.shortlist.InterviewActivity
import com.hidden.client.ui.activities.shortlist.ShortlistFeedbackActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

@SuppressLint("ViewConstructor")
class TimelineInterviewFeedbackTileView(
    context: Context,
    private val data: TimelineEntity,
    private val separator: Boolean
) :
    LinearLayout(context)
    , View.OnClickListener {

    private var txtDate: TextView
    private var txtInterview: TextView
    private var txtLocation: TextView
    private var txtInterviewers: TextView
    private var layoutInterviewers: ConstraintLayout
    private var imgSeparator: ImageView

    private var txtWaitingCandidate: TextView
    private var txtWaitingInterviewer: TextView

    private var layoutCandidateFeedbackRating: LinearLayout
    private var layoutInterviewerFeedbackRating: LinearLayout
    private var txtCandidateFeedbackRating: TextView
    private var txtInterviewerFeedbackRating: TextView
    private var btnCandidateFeedbackStatus: Button
    private var btnInterviewerFeedbackStatus: Button

    private var txtInterviewerMore: TextView
    private var imgInterviewer1: CircleImageView
    private var imgInterviewer2: CircleImageView
    private var imgInterviewer3: CircleImageView
    private var imgInterviewer4: CircleImageView

    private var topLayout: LinearLayout
    private var bottomLayout: LinearLayout

    init {
        inflate(context, R.layout.view_process_timeline_interview_feedback_tile, this)

        txtDate = findViewById(R.id.text_date)
        txtInterview = findViewById(R.id.text_interview)
        txtLocation = findViewById(R.id.text_location)
        txtInterviewers = findViewById(R.id.text_interviewer)
        layoutInterviewers = findViewById(R.id.layout_interviewer)
        imgSeparator = findViewById(R.id.image_separator)

        txtWaitingCandidate = findViewById(R.id.text_waiting_candidate)
        txtWaitingInterviewer = findViewById(R.id.text_waiting_interviewer)

        layoutCandidateFeedbackRating = findViewById(R.id.layout_candidate_feedback_rating)
        layoutInterviewerFeedbackRating = findViewById(R.id.layout_interviewer_feedback_rating)
        txtCandidateFeedbackRating = findViewById(R.id.text_candidate_feedback_rating)
        txtInterviewerFeedbackRating = findViewById(R.id.text_interviewer_feedback_rating)
        btnCandidateFeedbackStatus = findViewById(R.id.button_candidate_feedback_status)
        btnInterviewerFeedbackStatus = findViewById(R.id.button_interviewer_feedback_status)

        txtInterviewerMore = findViewById(R.id.text_interviewer_more)
        imgInterviewer1 = findViewById(R.id.image_interviewer_1)
        imgInterviewer2 = findViewById(R.id.image_interviewer_2)
        imgInterviewer3 = findViewById(R.id.image_interviewer_3)
        imgInterviewer4 = findViewById(R.id.image_interviewer_4)

        topLayout = findViewById(R.id.layout_timeline_interview_top)
        bottomLayout = findViewById(R.id.layout_timeline_interview_bottom)

        initView()
    }

    private fun initView() {
        if (separator) {
            imgSeparator.visibility = View.VISIBLE
        }

        txtInterview.text = data.description

        val interviewDate: Date? = HCDate.stringToDate(data.dateTime!!, null)

        val day: String = HCDate.dayPrefix(HCDate.dateToString(interviewDate!!,"d").safeValue())
        val month: String =  HCDate.dateToString(interviewDate, "MMMM").safeValue()
        val year: String = HCDate.dateToString(interviewDate, "yy").safeValue()
        val interviewDateText = "Completed on $day $month '$year"

        txtDate.text = interviewDateText
        txtLocation.text = data.location
        txtInterviewers.text = context!!.getString(R.string.interviewers)

        if (data.candidateFeedbackAverage == null) {
            txtWaitingCandidate.visibility = View.VISIBLE
        } else {
            layoutCandidateFeedbackRating.visibility = View.VISIBLE
            txtCandidateFeedbackRating.text = "%.1f".format(data.candidateFeedbackAverage.toDouble())

            btnCandidateFeedbackStatus.visibility = View.VISIBLE
            when {
                data.candidateFeedbackDecision.safeValue() == Enums.FeedbackDecisionType.PROGRESS.value -> {
                    btnCandidateFeedbackStatus.text = context.getString(R.string.progressing)
                    btnCandidateFeedbackStatus.setBackgroundResource(R.drawable.button_round_green_4)
                }
                data.candidateFeedbackDecision.safeValue() == Enums.FeedbackDecisionType.REJECT.value -> {
                    btnCandidateFeedbackStatus.text = context.getString(R.string.rejected)
                    btnCandidateFeedbackStatus.setBackgroundResource(R.drawable.button_round_red_4)
                }
                else -> {
                    btnCandidateFeedbackStatus.text = context.getString(R.string.decision_pending)
                    btnCandidateFeedbackStatus.setBackgroundResource(R.drawable.button_round_dark_gray_4)
                }
            }
        }

        if (data.interviewerFeedbackAverage == null) {
            txtWaitingInterviewer.visibility = View.VISIBLE
        } else {
            layoutInterviewerFeedbackRating.visibility = View.VISIBLE
            txtInterviewerFeedbackRating.text = "%.1f".format(data.interviewerFeedbackAverage!!.toDouble())

            btnInterviewerFeedbackStatus.visibility = View.VISIBLE
            when {
                data.interviewerFeedbackDecision.safeValue() == Enums.FeedbackDecisionType.PROGRESS.value -> {
                    btnInterviewerFeedbackStatus.text = context.getString(R.string.progressing)
                    btnInterviewerFeedbackStatus.setBackgroundResource(R.drawable.button_round_green_4)
                }
                data.interviewerFeedbackDecision.safeValue() == Enums.FeedbackDecisionType.REJECT.value -> {
                    btnInterviewerFeedbackStatus.text = context.getString(R.string.rejected)
                    btnInterviewerFeedbackStatus.setBackgroundResource(R.drawable.button_round_red_4)
                }
                else -> {
                    btnInterviewerFeedbackStatus.text = context.getString(R.string.decision_pending)
                    btnInterviewerFeedbackStatus.setBackgroundResource(R.drawable.button_round_dark_gray_4)
                }
            }
        }

        val interviewerCount = data.getInterviewParticipantList().size
        HCGlobal.getInstance().log(interviewerCount.toString())
        when {
            interviewerCount >= 4 -> {
                imgInterviewer1.visibility = View.VISIBLE
                imgInterviewer2.visibility = View.VISIBLE
                imgInterviewer3.visibility = View.VISIBLE
                imgInterviewer4.visibility = View.VISIBLE
                Glide.with(this).load(data.getInterviewParticipantList()[0].clientAvatar).into(imgInterviewer1)
                Glide.with(this).load(data.getInterviewParticipantList()[1].clientAvatar).into(imgInterviewer2)
                Glide.with(this).load(data.getInterviewParticipantList()[2].clientAvatar).into(imgInterviewer3)
                Glide.with(this).load(data.getInterviewParticipantList()[3].clientAvatar).into(imgInterviewer4)

                if (interviewerCount > 4) {
                    txtInterviewerMore.visibility = View.VISIBLE
                    txtInterviewerMore.text = """+${(interviewerCount - 4)}"""
                }
            }
            interviewerCount == 3 -> {
                imgInterviewer2.visibility = View.VISIBLE
                imgInterviewer3.visibility = View.VISIBLE
                imgInterviewer4.visibility = View.VISIBLE

                Glide.with(this).load(data.getInterviewParticipantList()[0].clientAvatar).into(imgInterviewer2)
                Glide.with(this).load(data.getInterviewParticipantList()[1].clientAvatar).into(imgInterviewer3)
                Glide.with(this).load(data.getInterviewParticipantList()[2].clientAvatar).into(imgInterviewer4)
            }
            interviewerCount == 2 -> {
                imgInterviewer3.visibility = View.VISIBLE
                imgInterviewer4.visibility = View.VISIBLE

                Glide.with(this).load(data.getInterviewParticipantList()[0].clientAvatar).into(imgInterviewer3)
                Glide.with(this).load(data.getInterviewParticipantList()[1].clientAvatar).into(imgInterviewer4)
            }
            interviewerCount == 1 -> {
                imgInterviewer4.visibility = View.VISIBLE
                Glide.with(this).load(data.getInterviewParticipantList()[0].clientAvatar).into(imgInterviewer4)
            }
        }

        topLayout.setOnClickListener(this)
        bottomLayout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.layout_timeline_interview_top -> {
                startInterviewActivity()
            }
            R.id.layout_timeline_interview_bottom -> {
                startInterviewActivity()
            }
        }
    }

    private fun startInterviewActivity() {

        val intent = Intent(HCGlobal.getInstance().currentActivity, InterviewActivity::class.java)
        val processId = data.pProcessId!!
        var interviewId = data.interviewId!!

        intent.putExtra("processId", processId)
        intent.putExtra("interviewId", interviewId)
        HCGlobal.getInstance().currentActivity.startActivity(intent)
    }
}