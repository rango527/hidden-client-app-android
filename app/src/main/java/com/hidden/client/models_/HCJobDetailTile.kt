package com.hidden.client.models_

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hidden.client.helpers.HCGlobal

class HCJobDetailTile {

    private var jobDetailTileId: Int? = 0

    private var jobDetailTileTitle: String? = ""
    private var jobDetailTileContent: String? = ""
    private var jobDetailTileType: String? = ""
    private var jobDetailTileSortOrder: Int? = 0
    private var jobDetailTileImg:String? = ""

    companion object {

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: ImageView, photoUrl: String?) {
            if (photoUrl !== null) {
                Glide.with(HCGlobal.getInstance().currentActivity).load(photoUrl!!).into(imageView)
            } else {
                imageView.visibility = View.GONE
            }
        }
    }

    fun getJobDetailTileTitle(): String? {
        return jobDetailTileTitle
    }

    fun setJobDetailTileTitle(jobDetailTileTitle: String?) {
        this.jobDetailTileTitle = jobDetailTileTitle
    }

    fun getJobDetailTileType(): String? {
        return jobDetailTileType
    }

    fun setJobDetailTileType(jobDetailTileType: String?) {
        this.jobDetailTileType = jobDetailTileType
    }
    fun getJobDetailTileSortOrder(): Int? {
        return jobDetailTileSortOrder
    }

    fun setJobDetailTileSortOrder(jobDetailTileSortOrder: Int?) {
        this.jobDetailTileSortOrder = jobDetailTileSortOrder
    }

    fun getJobDetailTileId(): Int? {
        return jobDetailTileId
    }

    fun setJobDetailTitleId(jobDetailTileId: Int?) {
        this.jobDetailTileId = jobDetailTileId
    }

    fun getJobDetailTileImg(): String? {
        return jobDetailTileImg
    }

    fun setJobDetailTileImg(jobDetailTileImg: String?) {
        this.jobDetailTileImg = jobDetailTileImg
    }

    fun getJobDetailTileContent(): String? {
        return jobDetailTileContent
    }

    fun setJobDetailTileContent(jobDetailTileContent: String?) {
        this.jobDetailTileContent = jobDetailTileContent
    }

}