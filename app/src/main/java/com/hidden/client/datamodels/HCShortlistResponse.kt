package com.hidden.client.datamodels

data class HCShortlistResponse(
    val client__client_id: Int,
    val client__full_name: String,
    val asset_client__cloudinary_url: String,
    val candidates: List<HCShortlistCandidateResponse>
) {

}