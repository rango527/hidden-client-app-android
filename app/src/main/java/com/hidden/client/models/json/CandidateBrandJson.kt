package com.hidden.client.models.json

import com.hidden.client.helpers.nullable.NullToZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CandidateBrandJson (

    @Json(name = "brand__brand_id")
    val brandId: Int,

    @Json(name = "asset__asset_id")
    val assetId: Int,

    @Json(name = "brand__name")
    val brandName: String,

    @Json(name = "asset__cloudinary_url")
    val assetImage: String,

    @Json(name = "p_candidate_id")
    @NullToZero
    var pCandidateId: Int = 0
){

}