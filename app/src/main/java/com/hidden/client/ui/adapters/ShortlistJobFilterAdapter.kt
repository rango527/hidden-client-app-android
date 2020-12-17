package com.hidden.client.ui.adapters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.HomeActivity
import com.hidden.client.ui.activities.project.ImageSliderActivity
import com.hidden.client.ui.fragments.home.shortlists.ShortlistsFragment

@Suppress("CAST_NEVER_SUCCEEDS")
class ShortlistJobFilterAdapter(fragment: ShortlistsFragment): RecyclerView.Adapter<ShortlistJobFilterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_row_shortlist_job_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jobList = HCGlobal.getInstance().ShortlistJobList[position]
        holder.textName.text = jobList.jobTitle + ", " + jobList.jobCityName
        if (jobList.jobTick) {
            holder.textName.setTextColor(Color.parseColor("#E74A5F"))
        } else {
            holder.textName.setTextColor(Color.parseColor("#8C8C8C"))
        }
        holder.itemView.setOnClickListener {
//            val slideDown = AnimationUtils.loadAnimation(HCGlobal.getInstance().currentActivity, R.anim.anim_slide_out_top)
//            layoutFilterPanel.startAnimation(slideDown)
//            layoutFilterPanel.visibility = View.INVISIBLE
//
//            layoutFilterContainer.animate()
//                .alpha(1f)
//                .setDuration(200)
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        layoutFilterContainer.visibility = View.GONE
//                        (activity as HomeActivity).toggleMask()
//                    }
//                })
            for ((index, ShortlistJob) in HCGlobal.getInstance().ShortlistJobList.withIndex()) {
                if (index != position && ShortlistJob.jobTick) {
                    holder.textName.setTextColor(Color.parseColor("#8C8C8C"))
                    ShortlistJob.jobTick = false
                }
            }
            holder.textName.setTextColor(Color.parseColor("#E74A5F"))
            HCGlobal.getInstance().ShortlistJobList[position].jobTick = true
            val intent = Intent(HCGlobal.getInstance().currentActivity, HomeActivity::class.java)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return HCGlobal.getInstance().ShortlistJobList.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.text_name)
    }

}