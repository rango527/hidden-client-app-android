package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.google.gson.JsonObject
import com.hidden.client.databinding.InterviewerItemViewBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.ProcessActivity
import com.hidden.client.ui.activities.process.HSGiveFeedbackActivity
import com.hidden.client.ui.viewmodels.main.InterviewerViewVM
import kotlinx.android.synthetic.main.viewpager_interviewer_item.view.*
import okhttp3.MediaType
import okhttp3.RequestBody

class InterviewerViewPagerAdapter(
    private val context: Context,
    private val interviewerViewVMList: List<InterviewerViewVM>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object` as View

    override fun getCount(): Int {
        return interviewerViewVMList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val binding: InterviewerItemViewBinding = InterviewerItemViewBinding.inflate(inflater, container, false)

        binding.viewModel = interviewerViewVMList[position]

        val questionListItem = interviewerViewVMList[position].feedbackQuestionViewAdapter.questionList

        val view = binding.root
        if (!interviewerViewVMList[position].isEmpty) {

            if (!interviewerViewVMList[position].isCurrentUser) {
                view.viewpager_feedback_waiting.visibility = View.VISIBLE
                view.viewpager_feedback_empty.visibility = View.GONE
                view.viewpager_feedback_question.visibility = View.GONE
                view.viewpager_feedback_comment.visibility = View.GONE
            }
            else {
                view.viewpager_feedback_waiting.visibility = View.GONE
                view.viewpager_feedback_empty.visibility = View.VISIBLE
                view.viewpager_feedback_question.visibility = View.GONE
                view.viewpager_feedback_comment.visibility = View.GONE

                // put feedbackId global variable
                val feedbackId = questionListItem[0].pFeedbackId
                HCGlobal.getInstance().currentFeedbackId = feedbackId
            }
        }
        else {
            view.viewpager_feedback_waiting.visibility = View.GONE
            view.viewpager_feedback_empty.visibility = View.GONE
            view.viewpager_feedback_question.visibility = View.VISIBLE
            view.viewpager_feedback_comment.visibility = View.VISIBLE
        }

        view.viewpager_feedback_question.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.viewpager_feedback_question.setHasFixedSize(true)
        view.viewpager_feedback_question.adapter = interviewerViewVMList[position].feedbackQuestionViewAdapter

        // nudge feedback parameters
        val processId = interviewerViewVMList[position].processId
        val feedbackId = interviewerViewVMList[position].feedbackId
        val clientId = interviewerViewVMList[position].clientId
        val body: JsonObject = JsonObject()
        body.addProperty("client_id", clientId)

        view.btn_nudge_for_feedback.setOnClickListener {
            (HCGlobal.getInstance().currentActivity as ProcessActivity).nudgeFeedback(processId!!, feedbackId!!, RequestBody.create(MediaType.parse("application/json"), body.toString()))
        }

        view.btn_give_feedback.setOnClickListener {
            val intent = Intent(context, HSGiveFeedbackActivity::class.java)

            intent.putExtra("processId", processId)
            intent.putExtra("isApprove", true)
            intent.putExtra("avatarName", interviewerViewVMList[position].fullName)

            context.startActivity(intent)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getPageWidth(position: Int): Float {
        return 1.0f
    }
}