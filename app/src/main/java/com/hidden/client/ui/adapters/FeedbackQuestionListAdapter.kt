package com.hidden.client.ui.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.helpers.Utility
import com.hidden.client.models.entity.FeedbackQuestionEntity

class FeedbackQuestionListAdapter(val context: Context, val questionList: List<FeedbackQuestionEntity>) : RecyclerView.Adapter<FeedbackQuestionListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.viewpager_shortlist_feedback_question_item, parent, false))
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.textQuestion.text = questionList[position].question
        holder?.rating.setRating(questionList[position].score.toFloat())

        // rating font awesome icons
        val filledList: List<Boolean> =
            when(questionList[position].score) {
                1 -> arrayListOf(true, false, false, false, false)
                2 -> arrayListOf(true, true, false, false, false)
                3 -> arrayListOf(true, true, true, false, false)
                4 -> arrayListOf(true, true, true, true, false)
                5 -> arrayListOf(true, true, true, true, true)
                else -> arrayListOf(false, false, false, false, false)
            }

        drawStarIcons(holder?.ratingIcons, filledList)
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val textQuestion: TextView = view.findViewById(R.id.text_question)
        val rating: RatingBar = view.findViewById(R.id.rating_feedback)
        val ratingIcons: List<TextView> = arrayListOf(
            view.findViewById(R.id.text_stage_icon1),
            view.findViewById(R.id.text_stage_icon2),
            view.findViewById(R.id.text_stage_icon3),
            view.findViewById(R.id.text_stage_icon4),
            view.findViewById(R.id.text_stage_icon5)
        )
    }

    fun drawStarIcons(ratingIcons: List<TextView>, filledList: List<Boolean>) {

        var index = 0
        for (ratingIcon in ratingIcons) {

            val fontType =
                if (filledList.get(index)) Typeface.createFromAsset(context!!.assets,"fonts/fontawesome_solid_pro.otf")
                else Typeface.createFromAsset(context!!.assets, "fonts/fontawesome_regular_pro.otf")

            ratingIcon.typeface = fontType

            ratingIcon.text = Utility.getStageClientTileIcon("f005")
            ratingIcon.setTextColor(Color.parseColor("#FF7A32"))

            index ++
        }

    }

}

