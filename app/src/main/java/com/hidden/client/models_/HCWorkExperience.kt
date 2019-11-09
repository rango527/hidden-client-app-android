package com.hidden.client.models_

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.HCDate
import com.hidden.client.helpers.HCGlobal
import de.hdodenhof.circleimageview.CircleImageView

class HCWorkExperience {

    private var experienceId: Int?              = 0

    private var experienceJobTitle: String?     =   ""
    private var experienceDescription: String?  =   ""
    private var experienceFrom: String?         =   ""
    private var experienceTo: String?           =   ""

    private var brandName: String?              =   ""
    private var brandLogoUrl: String?           =   ""

    constructor() {}

    constructor(
        experienceId: Int?,
        experienceJobTitle: String?,
        experienceDescription: String?,
        experienceFrom: String?,
        experienceTo: String?,
        brandName: String?,
        brandLogoUrl: String?
    ) {
        this.experienceId = experienceId
        this.experienceJobTitle = experienceJobTitle
        this.experienceDescription = experienceDescription
        this.experienceFrom = experienceFrom
        this.experienceTo = experienceTo
        this.brandName = brandName
        this.brandLogoUrl = brandLogoUrl
    }

    companion object {

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: CircleImageView, photoUrl: String) {
            Glide.with(HCGlobal.getInstance().currentActivity).load(photoUrl).into(imageView)
        }
    }

    fun getWorkingPeriod(): String {


        if (experienceFrom === null)
            return ""

        var strDate: String

        val fromDate = HCDate.stringToDate(experienceFrom!!, null)

        strDate = HCDate.dateToString(fromDate!!, "d MMMM yyyy").toString()

        if (experienceTo === null ) {

            return strDate + " - " + HCGlobal.getInstance().currentActivity.resources.getString(R.string.present)

        }

        val toDate = HCDate.stringToDate(experienceTo!!, null)

        return strDate + " - " + HCDate.dateToString(toDate!!, "d MMMM yyyy").toString()
    }

    fun getExperienceId() : Int? {
        return experienceId
    }

    fun setExperienceId(experienceId: Int?) {
        this.experienceId = experienceId
    }

    fun getExperienceJobTitle(): String?  {
        return experienceJobTitle
    }

    fun setExperienceJobTitle(experienceJobTitle: String?)  {
        this.experienceJobTitle = experienceJobTitle
    }

    fun getExperienceDescription(): String?  {
        return experienceDescription
    }

    fun setExperienceDescription(experienceDescription: String?)  {
        this.experienceDescription = experienceDescription
    }

    fun getExperienceFrom(): String?  {
        return experienceFrom
    }

    fun setExperienceFrom(experienceFrom: String?)  {
        this.experienceFrom = experienceFrom
    }

    fun getExperienceTo(): String?  {
        return experienceTo
    }

    fun setExperienceTo(experienceTo: String?)  {
        this.experienceTo = experienceTo
    }

    fun getBrandName(): String?  {
        return brandName
    }

    fun setBrandName(brandName: String?)  {
        this.brandName = brandName
    }

    fun getBrandLogoUrl(): String? {
        return brandLogoUrl
    }

    fun setBrandLogoUrl(brandLogoUrl: String?) {
        this.brandLogoUrl = brandLogoUrl
    }
}