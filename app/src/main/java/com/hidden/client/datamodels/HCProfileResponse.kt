package com.hidden.client.datamodels

data class HCProfileResponse(
    val client__client_id: Int,
    val client__full_name: String,

    val client__email: String,
    val client__job_title: String,

    val asset_client__cloudinary_url: String,
    val client__created_at: String,
    val client__last_updated: String,

    val client__is_admin: Boolean,

    val company: HCCompanyResponse

)