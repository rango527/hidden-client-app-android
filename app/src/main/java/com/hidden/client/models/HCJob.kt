package com.hidden.client.models

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hidden.client.helpers.HCGlobal
import com.rishabhharit.roundedimageview.RoundedImageView

class HCJob {

    private var jobPhoto: String? = ""
    private var jobTag: String? = ""
    private var jobTagColor: String? = ""
    private var jobTitle: String? = ""
    private var jobSubTitle: String? = ""
    private var jobID: Int? = 0

    private var jobType: String? = ""

    constructor() { }

    constructor(
        jobPhoto: String?,
        jobTag: String?,
        jobTagColor: String?,
        jobTitle: String?,
        jobSubTitle: String?,
        jobID: Int?,
        jobType: String?
    ) {
        this.jobPhoto = jobPhoto
        this.jobTag = jobTag
        this.jobTagColor = jobTagColor
        this.jobTitle = jobTitle
        this.jobSubTitle = jobSubTitle
        this.jobID = jobID
        this.jobType = jobType
    }

    companion object {

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: RoundedImageView, photoUrl: String) {
            Glide.with(HCGlobal.getInstance().currentActivity).load(photoUrl).into(imageView)
        }
    }

    fun getJobPhoto(): String? {
        return jobPhoto
    }

    fun setJobPhoto(jobPhoto:  String?) {
        this.jobPhoto = jobPhoto
    }

    fun getJobTag(): String? {
        return jobTag
    }

    fun setJobTag(jobTag: String?) {
        this.jobTag = jobTag
    }

    fun getJobTagColor(): String? {
        return jobTagColor
    }

    fun setJobTagColor(jobTagColor: String?) {
        this.jobTagColor = jobTagColor
    }

    fun getJobTitle(): String? {
        return jobTitle
    }

    fun setJobTitle(jobTitle: String?) {
        this.jobTitle = jobTitle
    }

    fun getJobSubTitle(): String? {
        return jobSubTitle
    }

    fun setJobSubTitle(jobSubTitle: String?) {
        this.jobSubTitle = jobSubTitle
    }

    fun getJobID(): Int? {
        return jobID
    }

    fun setJobID(jobID: Int?) {
        this.jobID = jobID
    }

    fun getJobType(): String? {
        return jobType
    }

    fun setJobType(jobType: String?) {
        this.jobType = jobType
    }
}