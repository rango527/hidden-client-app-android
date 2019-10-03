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
import com.hidden.client.models.HCProcess
import com.hidden.client.ui.activities.HCProcessActivity
import com.hidden.client.ui.activities.HCYourJobActivity
import com.hidden.client.ui.viewholders.HCProcessVH
import com.hidden.client.ui.viewholders.HCYourJobVH

class HCProcessAdapter : RecyclerView.Adapter<HCProcessVH> {

    var list: MutableList<HCProcess> = mutableListOf()

    var context: Context

    constructor(list: MutableList<HCProcess>, context: Context) {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCProcessVH {
        var rv: View
        var holder: HCProcessVH
        rv = LayoutInflater.from(parent.context).inflate(R.layout.list_row_process, parent, false)
        holder = HCProcessVH(rv)

        return holder
    }

    override fun onBindViewHolder(holder: HCProcessVH, position: Int) {
        var process: HCProcess

        process = list.get(position)

        holder.imgPhoto.setImageResource(process.getCandidatePhoto())
        holder.textName.setText(process.getCandidateName())
        holder.textFor.setText(String.format(context.resources.getString(R.string.job_and_location)
            , process.getCandidateJob(), process.getCandidateJob()))

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, HCProcessActivity::class.java)
            HCGlobal.getInstance(context).g_currentActivity.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

}