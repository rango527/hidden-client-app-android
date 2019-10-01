package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.viewholders.HCImageVH

class HCImageAdapter : RecyclerView.Adapter<HCImageVH> {

    var list: Array<Int> = arrayOf()
    var context: Context
    var imageType: Int  // 0: Circle  1: Rounded

    constructor(list: Array<Int>, imageType:Int, context: Context) {
        this.list = list
        this.imageType = imageType
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HCImageVH {
        var rv: View
        var holder: HCImageVH

        if (imageType == HCGlobal.getInstance(context).IMAGE_TYPE_CIRCLE) {
            rv = LayoutInflater.from(parent.context).inflate(R.layout.list_row_circle_image, parent, false)
        } else {
            rv = LayoutInflater.from(parent.context).inflate(R.layout.list_row_rounded_image, parent, false)
        }

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