package com.hidden.client.datamodels

data class HCJobDetailResponse(
    val client_asset__cloudinary_url: String,
    val client__full_name: String,
    val job__title: String,
    val job__status: String,
    val job__salary_from: String,
    val job__salary_to: String,
    val job_city__name: String,
    val company__name: String,
    val company__company_id: Int,
    val company_logo_asset__cloudinary_url: String,
    val company_cover_image_asset__cloudinary_url: String,
    val job_cover_image_asset__cloudinary_url: String,
    val company__status: String,
    val company_size__company_size_id: Int,
    val company_size__name: String,
    val company_type__company_type_id: Int,
    val company_type__name: String,
    val company__hidden_says: String,
    val job__created_at: String,
    val job__last_updated: String,
    val job__tiles: List<HCJobTileResponse>
)