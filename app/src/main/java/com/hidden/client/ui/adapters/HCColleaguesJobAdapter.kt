package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.models.HCJob
import com.hidden.client.ui.viewholders.HCColleagueJobVH
import com.hidden.client.ui.viewholders.HCYourJobVH

class HCColleaguesJobAdapter : RecyclerView.Adapter<HCColleagueJobVH> {

    var list: MutableList<HCJob> = mutableListOf()

    var context: Context

    constructor(list: MutableList<HCJob>, context: Context) {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCColleagueJobVH {
        var rv: View
        var holder: HCColleagueJobVH
        rv = LayoutInflater.from(parent.context).inflate(R.layout.list_row_colleague_job, parent, false)
        holder = HCColleagueJobVH(rv)

        return holder
    }

    override fun onBindViewHolder(holder: HCColleagueJobVH, position: Int) {
        var job: HCJob

        job = list.get(position)

        holder.textJobTitle.setText(job.getJobTitle())
        holder.textJobLocation.setText(job.getJobLocation())
    }

    override fun getItemCount(): Int {
        return list.size
    }

}