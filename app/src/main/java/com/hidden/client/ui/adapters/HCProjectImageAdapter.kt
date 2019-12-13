package com.hidden.client.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.project.ImageSliderActivity
import com.hidden.client.ui.viewholders.HCImageVH

class HCProjectImageAdapter : RecyclerView.Adapter<HCImageVH> {

    var list: Array<String> = arrayOf()
    var context: Context

    constructor(list: Array<String>, context: Context) {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCImageVH {

        val rv: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_row_hightlight_image, parent, false)

        val holder: HCImageVH = HCImageVH(rv)

        return holder
    }

    override fun onBindViewHolder(holder: HCImageVH, position: Int) {

        var imgUrl: String = list[position]
        Glide.with(context).load(imgUrl).into(holder.img)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, ImageSliderActivity::class.java)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

}