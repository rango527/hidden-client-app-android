package com.hidden.client.datamodels

data class HCCandidateProjectAssets (
    val project_asset__asset_id: Int,
    val project_asset__asset_type: String,
    val project_asset__cloudinary_url: String,
    val project_asset__is_main_image: Boolean
)
