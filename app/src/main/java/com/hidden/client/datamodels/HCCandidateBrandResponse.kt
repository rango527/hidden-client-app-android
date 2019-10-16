package com.hidden.client.datamodels

/*
{
    "brand__brand_id": "736",
    "asset__asset_id": "747",
    "brand__name": "Animals",
    "asset__cloudinary_url": "https://res.cloudinary.com/dioyg7htb/image/upload/v1532537444/hidden/brands/736/logo/5b58aa62b5962.jpg"
}
 */
data class HCCandidateBrandResponse(
    val brand__brand_id: Int,
    val asset__asset_id: Int,
    val brand__name: String,
    val asset__cloudinary_url: String
) {

}