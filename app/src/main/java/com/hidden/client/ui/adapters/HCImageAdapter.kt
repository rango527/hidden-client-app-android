package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.HCConstants
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.ProjectDetailActivity
import com.hidden.client.ui.viewholders.HCImageVH

class HCImageAdapter : RecyclerView.Adapter<HCImageVH> {

    var list: Array<String> = arrayOf()
    var context: Context
    var imageType: Int  // 0: Circle  1: Roundex
    var clickable: Boolean = false
    var parentIndex = 0

    constructor(list: Array<String>, imageType:Int, context: Context) {
        this.list = list
        this.imageType = imageType
        this.context = context
    }

    constructor(list: Array<String>, imageType:Int, clickable: Boolean, parentIndex: Int, context: Context) {
        this.list = list
        this.imageType = imageType
        this.context = context
        this.clickable = clickable
        this.parentIndex = parentIndex
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCImageVH {
        var rv: View
        var holder: HCImageVH

        if (imageType == HCConstants.IMAGE_TYPE_CIRCLE) {
            rv = LayoutInflater.from(parent.context).inflate(R.layout.list_row_circle_image, parent, false)
        } else {
            rv = LayoutInflater.from(parent.context).inflate(R.layout.list_row_rounded_image, parent, false)
        }

        holder = HCImageVH(rv)

        return holder
    }

    override fun onBindViewHolder(holder: HCImageVH, position: Int) {

        var imgUrl: String = list[position]
        Glide.with(context).load(imgUrl).into(holder.img)

        if (clickable) {
            holder.itemView.setOnClickListener(View.OnClickListener {
                HCGlobal.getInstance().currentIndex = parentIndex
                HCGlobal.getInstance().currentProjectIndex = position
                val intent = Intent(HCGlobal.getInstance().currentActivity, ProjectDetailActivity::class.java)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            })
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}