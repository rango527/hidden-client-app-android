package com.hidden.client.models

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hidden.client.helpers.HCGlobal
import de.hdodenhof.circleimageview.CircleImageView

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
        fun setImageViewResource(imageView: CircleImageView, photoUrl: String) {
            Glide.with(HCGlobal.getInstance().currentActivity).load(photoUrl).into(imageView)
        }
    }

    fun getJobDetailTitleTitle(): String? {
        return jobDetailTileTitle
    }

    fun setJobDetailTileTitle(jobDetailTileType: String?) {
        this.jobDetailTileType = jobDetailTileType
    }

    fun getJobDetailTitleType(): String? {
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

    fun getToDetailTileImg(): String? {
        return jobDetailTileImg
    }

    fun setJobDetailTileImg(jobDetailTileImg: String?) {
        this.jobDetailTileImg = jobDetailTileImg
    }

    fun getJobDetailTileContent(): String? {
        return jobDetailTileContent
    }

    fun setJobDetailTileContent(jobDetailTitleContent: String?) {
        this.jobDetailTileContent = jobDetailTileContent
    }

}