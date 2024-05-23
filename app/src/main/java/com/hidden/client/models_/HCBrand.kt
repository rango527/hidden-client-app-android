package com.hidden.client.models_

class HCBrand(brandPhotoUrl: String) {

    private lateinit var brandPhotoUrl: String

    init {
        setBrandPhotoUrl(brandPhotoUrl)
    }

    fun getBrandPhotoUrl(): String {
        return brandPhotoUrl
    }

    fun setBrandPhotoUrl(brandPhotoUrl: String) {
        this.brandPhotoUrl = brandPhotoUrl
    }

}