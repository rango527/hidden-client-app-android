package com.hidden.client.models

class HCBrand {
    private lateinit var brandPhotoUrl: String

    constructor() { }

    constructor(brandPhotoUrl: String) {
        this.brandPhotoUrl = brandPhotoUrl
    }

    fun getBrandPhotoUrl(): String {
        return brandPhotoUrl
    }

    fun setBrandPhotoUrl(brandPhotoUrl: String) {
        this.brandPhotoUrl = brandPhotoUrl
    }

}