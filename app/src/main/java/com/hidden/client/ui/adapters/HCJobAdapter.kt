package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCJobBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.HCJobActivity
import com.hidden.client.ui.viewholders.HCJobVH
import com.hidden.client.ui.viewmodels___.HCJobViewModel

class HCJobAdapter(
    private val context:Context,
    private val list: ArrayList<HCJobViewModel>) : RecyclerView.Adapter<HCJobVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCJobVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val jobBinding: HCJobBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_job, parent, false)

        return HCJobVH(jobBinding)
    }

    override fun onBindViewHolder(holder: HCJobVH, position: Int) {

        val jobModel = list[position]
        holder.bind(jobModel)

        holder.itemView.setOnClickListener(View.OnClickListener {

            val intent = Intent(context, HCJobActivity::class.java)
            intent.putExtra("jobId", jobModel.getJob().getJobID().toString())
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

}