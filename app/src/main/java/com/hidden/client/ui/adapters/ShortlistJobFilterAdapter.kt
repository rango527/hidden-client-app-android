package com.hidden.client.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.fragments.home.shortlists.ShortlistsFragment

@Suppress("CAST_NEVER_SUCCEEDS")
class ShortlistJobFilterAdapter(
    private var ShortlistsFragment: ShortlistsFragment
): RecyclerView.Adapter<ShortlistJobFilterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_row_shortlist_job_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jobList = HCGlobal.getInstance().ShortlistJobList[position]
        holder.textName.text = jobList.jobTitle + ", " + jobList.jobCityName

        if (jobList.jobTick) {
            holder.textName.setTextColor(Color.parseColor("#E74A5F"))
        } else {
            holder.textName.setTextColor(Color.parseColor("#8C8C8C"))
        }

        holder.itemView.setOnClickListener {
            for ((index, ShortlistJob) in HCGlobal.getInstance().ShortlistJobList.withIndex()) {
                if (index != position && ShortlistJob.jobTick) {
                    ShortlistJob.jobTick = false
                }
            }

            holder.textName.setTextColor(Color.parseColor("#E74A5F"))
            HCGlobal.getInstance().ShortlistJobList[position].jobTick = true

            ShortlistsFragment.layoutSlideDown()
            ShortlistsFragment.initCandidateVMList()
        }
    }

    override fun getItemCount(): Int {
        return HCGlobal.getInstance().ShortlistJobList.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.text_name)
    }

}