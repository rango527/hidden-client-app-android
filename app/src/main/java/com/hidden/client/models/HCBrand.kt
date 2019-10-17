package com.hidden.client.models

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hidden.client.helpers.HCGlobal
import com.rishabhharit.roundedimageview.RoundedImageView
import de.hdodenhof.circleimageview.CircleImageView

class HCBrand {

    private lateinit var brandPhotoUrl: String

    companion object {

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: CircleImageView, photoUrl: String) {
            Glide.with(HCGlobal.getInstance().currentActivity).load(photoUrl).into(imageView)
        }
    }

    constructor() { }

    constructor(brandPhotoUrl: String) {
        setBrandPhotoUrl(brandPhotoUrl)
    }

    fun getBrandPhotoUrl(): String {
        return brandPhotoUrl
    }

    fun setBrandPhotoUrl(brandPhotoUrl: String) {
        this.brandPhotoUrl = brandPhotoUrl
    }

}