package com.hidden.client.datamodels

data class HCCompanyPeopleResponse(
    val company_person__company_person_id: Int,
    val company_person__full_name: String,
    val company_person__job_title: String,
    val company_person__content: String,
    val company_person__sort_order: Int,
    val company_person_asset__cloudinary_url: String
)