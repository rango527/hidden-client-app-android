package com.hidden.client.datamodels

data class HCCompanyResponse (
    val company__company_id: Int,
    val company__name: String,
    val company_logo_asset__cloudinary_url: String,
    val company_cover_image_asset__cloudinary_url: String,
    val company__status: String,
    val company_size__company_size_id: Int,
    val company_size__name: String,
    val company_type__company_type_id: Int,
    val company_type__name: String,
    val company__hidden_says: String,
    val company__created_at: String,
    val company__last_updated: String,
    val company__cities: List<HCCityResponse>,
    val company__people: List<HCCompanyPeopleResponse>,
    val company__tiles: List<HCJobTileResponse>,
    val jobs: List<HCJobResponse>
)