package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCJob
import com.hidden.client.ui.activities.HCYourJobActivity
import com.hidden.client.ui.viewholders.HCYourJobVH

class HCYourJobAdapter : RecyclerView.Adapter<HCYourJobVH> {

    var list: MutableList<HCJob> = mutableListOf()

    var context: Context

    constructor(list: MutableList<HCJob>, context: Context) {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCYourJobVH {
        var rv: View
        var holder: HCYourJobVH
        rv = LayoutInflater.from(parent.context).inflate(R.layout.list_row_job, parent, false)
        holder = HCYourJobVH(rv)

        return holder
    }

    override fun onBindViewHolder(holder: HCYourJobVH, position: Int) {
        var job: HCJob

        job = list.get(position)

        holder.textJobTitle.setText(job.getJobTitle())
        holder.textJobLocation.setText(job.getJobLocation())

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, HCYourJobActivity::class.java)
            HCGlobal.getInstance(context).g_currentActivity.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

}