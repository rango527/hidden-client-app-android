package com.hidden.client.ui.adapters

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.FeedbackQuestionEntity
import com.hidden.client.ui.activities.shortlist.FeedbackActivity
import java.util.*

@Suppress("NAME_SHADOWING")
class FeedbackQuestionViewPagerAdapter(
    private val context: Context,
    private val questionList: List<FeedbackQuestionEntity>,
    private val isApprove: Boolean,
    private val viewPagerFeedback: ViewPager
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object` as View

    override fun getCount(): Int {
        return questionList.size + 1
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View

        if (position < questionList.size) {
            view = inflater.inflate(R.layout.viewpager_feedback_question_item, container, false)

            val textQuestion: TextView = view.findViewById(R.id.text_question)
            textQuestion.text = questionList[position].question

            val rating: RatingBar = view.findViewById(R.id.rating_feedback)
            rating.setOnRatingBarChangeListener { _, rating, _ ->
                questionList[position].score = rating.toInt()

                // change default speed of viewpager auto sliding
                val handler = Handler()
                val updateCurrentItem = Runnable {
                    viewPagerFeedback.currentItem = viewPagerFeedback.currentItem + 1
                }

                val swipeTimer = Timer()
                swipeTimer.schedule(object : TimerTask() {
                    override fun run() {
                        handler.post(updateCurrentItem)
                    }
                }, 500)
            }
        } else {
            view = inflater.inflate(R.layout.viewpager_feedback_comment, container, false)

            val layoutSubmit: LinearLayout = view.findViewById(R.id.layout_submit_feedback)
            val txtApprove: TextView = view.findViewById(R.id.text_approve)
            val editComment: EditText = view.findViewById(R.id.edit_comment)

            if (isApprove) {
                layoutSubmit.background = context.getDrawable(R.drawable.panel_bottom_rounded_green)
                txtApprove.text = context.getString(R.string.approve)
            } else {
                layoutSubmit.background = context.getDrawable(R.drawable.panel_bottom_rounded_red)
                txtApprove.text = context.getString(R.string.reject)
            }
            layoutSubmit.setOnClickListener {
                (HCGlobal.getInstance().currentActivity as FeedbackActivity).submitFeedback(questionList, editComment.text.toString())
            }
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

    fun getQuestionList(): List<FeedbackQuestionEntity> {
        return questionList
    }
}