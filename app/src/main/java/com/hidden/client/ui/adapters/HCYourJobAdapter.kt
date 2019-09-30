package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.models.HCJob
import com.hidden.client.ui.viewholders.HCJobVH

class HCYourJobAdapter : RecyclerView.Adapter<HCJobVH> {

    var list: MutableList<HCJob> = mutableListOf()

    lateinit var context: Context

    constructor(list: MutableList<HCJob>, context: Context) {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCJobVH {
        var rv: View
        var holder: HCJobVH
        rv = LayoutInflater.from(parent!!.context).inflate(R.layout.list_row_job, parent, false)
        holder = HCJobVH(rv)

        return holder
    }

    override fun onBindViewHolder(holder: HCJobVH, position: Int) {
        var job: HCJob

        job = list.get(position)

        holder!!.textJobTitle.setText(job.getJobTitle())
        holder!!.textJobLocation.setText(job.getJobLocation())
    }

    override fun getItemCount(): Int {
        return list.size
    }

}