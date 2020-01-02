package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hidden.client.R
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

        return HCImageVH(rv)
    }

    override fun onBindViewHolder(holder: HCImageVH, position: Int) {

        var imgUrl: String = list[position]
        Glide.with(context).load(imgUrl).into(holder.img)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}