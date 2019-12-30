package com.hidden.client.models_

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hidden.client.helpers.HCGlobal
import de.hdodenhof.circleimageview.CircleImageView

class HCBrand {

    private lateinit var brandPhotoUrl: String



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