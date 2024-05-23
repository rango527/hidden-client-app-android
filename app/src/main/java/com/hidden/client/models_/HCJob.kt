package com.hidden.client.models_

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.rishabhharit.roundedimageview.RoundedImageView

class HCJob {

    private var jobPhoto: String? = ""
    private var jobTag: String? = ""
    private var jobTagColor: String? = ""
    private var jobTitle: String? = ""
    private var jobSubTitle: String? = ""
    private var jobID: Int? = 0

    private var jobType: String? = ""       // Your or Colleague

    // Additional Job Info
    private var jobCompany: String? = ""
    private var jobSalaryFrom: Float? = 0f
    private var jobSalaryTo: Float? = 0f

    private var jobCoverPhoto: String? = ""
    private var jobCompanyPhoto: String? = ""
    private var jobCompanyName: String? = ""
    private var jobHiddenSays: String? = ""

    private var jobDetailTilesList: ArrayList<HCJobDetailTile> = arrayListOf()

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
        fun setImageViewResource(imageView: RoundedImageView, photoUrl: String?) {
            if (photoUrl.safeValue() != "") {
                Glide.with(HCGlobal.getInstance().currentActivity).load(photoUrl.safeValue()).into(imageView)
            }
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

    fun getJobCompany(): String? {
        return jobCompany
    }

    fun setJobCompany(jobCompany: String?) {
        this.jobCompany = jobCompany
    }

    fun getJobSalaryFrom(): Float? {
        return jobSalaryFrom
    }

    fun setJobSalaryFrom(jobSalaryFrom: Float?) {
        this.jobSalaryFrom = jobSalaryFrom
    }

    fun getJobSalaryTo(): Float? {
        return jobSalaryTo
    }

    fun setJobSalaryTo(jobSalaryTo: Float?) {
        this.jobSalaryTo = jobSalaryTo
    }

    fun getJobCoverPhoto(): String? {
        return jobCoverPhoto
    }

    fun setJobCoverPhoto(jobCoverPhoto: String?) {
        this.jobCoverPhoto = jobCoverPhoto
    }

    fun getJobCompanyPhoto(): String? {
        return jobCompanyPhoto
    }

    fun setJobCompanyPhoto(jobCompanyPhoto: String?) {
        this.jobCoverPhoto = jobCompanyPhoto
    }

    fun getJobCompanyName(): String? {
        return jobCompanyName
    }

    fun setJobCompanyName(jobCompanyName: String?) {
        this.jobCompanyName = jobCompanyName
    }

    fun getJobHiddenSays(): String? {
        return jobHiddenSays
    }

    fun setJobHiddenSays(jobHiddenSays: String?) {
        this.jobHiddenSays = jobHiddenSays
    }

    fun getJobDetailTileList(): ArrayList<HCJobDetailTile> {
        return jobDetailTilesList
    }

    fun setJobDetailTileList(jobDetailTileList: ArrayList<HCJobDetailTile>) {
        this.jobDetailTilesList = jobDetailTilesList
    }

}