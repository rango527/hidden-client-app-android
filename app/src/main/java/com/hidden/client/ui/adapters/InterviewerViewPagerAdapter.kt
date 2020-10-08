package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.hidden.client.R
import com.hidden.client.databinding.InterviewerItemViewBinding
import com.hidden.client.ui.activities.process.HSGiveFeedbackActivity
import com.hidden.client.ui.viewmodels.main.InterviewerViewVM
import kotlinx.android.synthetic.main.viewpager_interviewer_item.view.*

class InterviewerViewPagerAdapter(
    private val context: Context,
    private val interviewerViewVMList: List<InterviewerViewVM>
) : PagerAdapter(), View.OnClickListener {

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object` as View

    override fun getCount(): Int {
        return interviewerViewVMList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val binding: InterviewerItemViewBinding = InterviewerItemViewBinding.inflate(inflater, container, false)

        binding.viewModel = interviewerViewVMList[position]
        val testtest = interviewerViewVMList[position].feedbackQuestionViewAdapter
        Log.d("testtest", "testtesttest $testtest")
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

        view.btn_nudge_for_feedback.setOnClickListener(this)
        view.btn_give_feedback.setOnClickListener(this)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getPageWidth(position: Int): Float {
        return 1.0f
    }

    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.btn_nudge_for_feedback -> {
                // nudge for feedback
            }
            R.id.btn_give_feedback -> {
                // give feedback
                val intent = Intent(context, HSGiveFeedbackActivity::class.java)
//                val intent = Intent(context, FeedbackActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}