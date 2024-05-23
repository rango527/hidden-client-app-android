package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.models.entity.InterviewerEntity
import de.hdodenhof.circleimageview.CircleImageView

class InterviewerItemListAdapter(private val context: Context, private val interviewerList: List<InterviewerEntity>): RecyclerView.Adapter<InterviewerItemListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_row_interviewer_item, parent, false))
    }

    override fun getItemCount(): Int {
        return interviewerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(interviewerList[position].clientAvatar).into(holder?.imgPhoto)
        holder?.textName.text = interviewerList[position].fullName
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val imgPhoto: CircleImageView = view.findViewById(R.id.img_photo)
        val textName: TextView = view.findViewById(R.id.text_name)
    }

}