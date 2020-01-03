package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.HCProcessBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.HCProcessActivity
import com.hidden.client.ui.viewholders.HCProcessVH
import com.hidden.client.ui.viewmodels___.HCProcessViewModel

class HCProcessAdapter(
    private val context:Context,
    private val list: ArrayList<HCProcessViewModel>) : RecyclerView.Adapter<HCProcessVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCProcessVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val processBinding: HCProcessBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_row_process, parent, false)

        return HCProcessVH(processBinding)
    }

    override fun onBindViewHolder(holder: HCProcessVH, position: Int) {

        val processModel = list[position]
        holder.bind(processModel)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, HCProcessActivity::class.java)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}