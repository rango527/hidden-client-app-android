package com.hidden.client.datamodels

data class HCCandidateProjectResponse(
    val project__project_id: Int,
    val project__title: String,
    val project__brief: String,
    val project__activity: String,
    val brand__name: String,
    val brand_logo__cloudinary_url: String,
    val candidate__project_assets: List<HCCandidateProjectAssets> = listOf()
)