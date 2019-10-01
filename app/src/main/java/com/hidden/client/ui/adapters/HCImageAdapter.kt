package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.models.HCJob
import com.hidden.client.ui.viewholders.HCImageVH
import com.hidden.client.ui.viewholders.HCJobVH

class HCImageAdapter : RecyclerView.Adapter<HCImageVH> {

    var list: Array<Int> = arrayOf()

    var context: Context

    constructor(list: Array<Int>, context: Context) {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCImageVH {
        var rv: View
        var holder: HCImageVH
        rv = LayoutInflater.from(parent.context).inflate(R.layout.list_row_image, parent, false)
        holder = HCImageVH(rv)

        return holder
    }

    override fun onBindViewHolder(holder: HCImageVH, position: Int) {

        var imgResId: Int = list[position]
        holder.img.setImageResource(imgResId)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}