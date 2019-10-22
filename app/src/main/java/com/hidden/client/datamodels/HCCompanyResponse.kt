package com.hidden.client.datamodels

data class HCCompanyResponse (
    val company__company_id: Int,
    val company__name: String,
    val company_logo_asset__cloudinary_url: String
) {

}