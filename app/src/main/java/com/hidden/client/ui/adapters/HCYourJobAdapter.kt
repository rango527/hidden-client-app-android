package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCYourJobBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCJob
import com.hidden.client.ui.activities.HCYourJobActivity
import com.hidden.client.ui.viewholders.HCYourJobVH
import com.hidden.client.ui.viewmodels.HCYourJobViewModel

class HCYourJobAdapter(
    private val context:Context,
    private val list: ArrayList<HCYourJobViewModel>) : RecyclerView.Adapter<HCYourJobVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCYourJobVH {

//        var rv: View
//        var holder: HCYourJobVH
//        rv = LayoutInflater.from(parent.context).inflate(R.layout.list_row_job, parent, false)
//        holder = HCYourJobVH(rv)
//        return holder

        val layoutInflater = LayoutInflater.from(parent.context)
        val jobBinding: HCYourJobBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_job, parent, false)

        return HCYourJobVH(jobBinding)
    }

    override fun onBindViewHolder(holder: HCYourJobVH, position: Int) {

        val jobModel = list[position]
        holder.bind(jobModel)

//        holder.textJobTitle.setText(job.getJobTitle())
//        holder.textJobLocation.setText(job.getJobLocation())

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, HCYourJobActivity::class.java)
            HCGlobal.getInstance(context).g_currentActivity.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

}