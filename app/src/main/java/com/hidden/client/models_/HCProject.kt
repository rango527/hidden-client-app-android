package com.hidden.client.models_

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hidden.client.helpers.HCGlobal
import de.hdodenhof.circleimageview.CircleImageView

class HCProject {

    private var projectId: Int = 0

    private lateinit var projectTitle: String
    private lateinit var projectBrief: String
    private lateinit var projectActivity: String

    private lateinit var brandName: String
    private lateinit var brandLogoUrl: String

    constructor(
        projectId: Int,
        projectTitle: String,
        projectBrief: String,
        projectActivity: String,
        brandName: String,
        brandLogoUrl: String
    ) {
        setProjectId(projectId)
        setProjectTitle(projectTitle)
        setProjectBrief(projectBrief)
        setProjectActivity(projectActivity)
        setBrandName(brandName)
        setBrandLogoUrl(brandLogoUrl)
    }

    companion object {

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: CircleImageView, photoUrl: String) {
            Glide.with(HCGlobal.getInstance().currentActivity).load(photoUrl).into(imageView)
        }
    }

    fun getProjectId() : Int {
        return projectId
    }

    fun setProjectId(projectId: Int) {
        this.projectId = projectId
    }

    fun getProjectTitle(): String  {
        return projectTitle
    }

    fun setProjectTitle(projectTitle: String)  {
        this.projectTitle = projectTitle
    }

    fun getProjectBrief(): String  {
        return projectBrief
    }

    fun setProjectBrief(projectBrief: String)  {
        this.projectBrief = projectBrief
    }

    fun getProjectActivity(): String  {
        return projectActivity
    }

    fun setProjectActivity(projectActivity: String)  {
        this.projectActivity = projectActivity
    }

    fun getBrandName(): String  {
        return brandName
    }

    fun setBrandName(brandName: String)  {
        this.brandName = brandName
    }

    fun getBrandLogoUrl(): String {
        return brandLogoUrl
    }

    fun setBrandLogoUrl(brandLogoUrl: String) {
        this.brandLogoUrl = brandLogoUrl
    }

}