package com.hidden.client.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.hidden.client.R
import com.hidden.client.databinding.ShortlistReviewerItemViewBinding
import com.hidden.client.ui.viewmodels.main.ShortlistReviewerViewVM
import kotlinx.android.synthetic.main.viewpager_candidate_item.view.*
import kotlinx.android.synthetic.main.viewpager_shortlist_feedback_item.view.*

class ShortlistFeedbackViewPagerAdapter(
    private val context: Context,
    private val shortlistReviewerViewVMList: List<ShortlistReviewerViewVM>
) : PagerAdapter() {

        override fun isViewFromObject(view: View, `object`: Any): Boolean =
            view == `object` as View

        override fun getCount(): Int {
            return shortlistReviewerViewVMList.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): View {

            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val binding: ShortlistReviewerItemViewBinding = ShortlistReviewerItemViewBinding.inflate(inflater, container, false)

            val view = binding.root

            binding.viewModel = shortlistReviewerViewVMList[position]

            val feedbackStatus: Button = view.findViewById(R.id.button_interviewer_feedback_status)

            when (shortlistReviewerViewVMList[position].vote) {
                "ACCEPTED" -> {
                    feedbackStatus.setBackgroundResource(R.drawable.button_round_green_4)
                }
                "REJECTED" -> {
                    feedbackStatus.setBackgroundResource(R.drawable.button_round_red_4)
                }
                else -> {
                    feedbackStatus.setBackgroundResource(R.drawable.button_round_dark_gray_4)
                }
            }

            if (!shortlistReviewerViewVMList[position].isEmpty) {
                view.viewpager_feedback_empty.visibility = View.VISIBLE
                view.viewpager_feedback_question.visibility = View.GONE
                view.viewpager_feedback_comment.visibility = View.GONE
            }
            else {
                view.viewpager_feedback_empty.visibility = View.GONE
                view.viewpager_feedback_question.visibility = View.VISIBLE
                view.viewpager_feedback_comment.visibility = View.VISIBLE
            }

            view.viewpager_feedback_empty.text = shortlistReviewerViewVMList[position].fullName + " did not give feedback at shortlist stage"

            view.viewpager_feedback_question.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            view.viewpager_feedback_question.setHasFixedSize(true)

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