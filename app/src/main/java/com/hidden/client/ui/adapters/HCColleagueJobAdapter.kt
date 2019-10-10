package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCColleagueJobBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.HCYourJobActivity
import com.hidden.client.ui.viewholders.HCColleagueJobVH
import com.hidden.client.ui.viewmodels.HCColleagueJobViewModel

class HCColleagueJobAdapter(
    private val context:Context,
    private val list: ArrayList<HCColleagueJobViewModel>) : RecyclerView.Adapter<HCColleagueJobVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCColleagueJobVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val jobBinding: HCColleagueJobBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_colleague_job, parent, false)

        return HCColleagueJobVH(jobBinding)
    }

    override fun onBindViewHolder(holder: HCColleagueJobVH, position: Int) {

        val jobModel = list[position]
        holder.bind(jobModel)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, HCYourJobActivity::class.java)
            HCGlobal.getInstance(context).g_currentActivity.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

}