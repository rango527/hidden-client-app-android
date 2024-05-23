package com.hidden.client.datamodels

data class HCCandidateResponse(
    val candidate__candidate_id: Int,
    val asset_candidate__cloudinary_url: String,
    val candidate__first_name: String,
    val candidate__surname: String,
    val candidate__full_name: String
)