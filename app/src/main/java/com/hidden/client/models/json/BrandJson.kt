package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.helpers.nullable.NullToZero
import com.hidden.client.models.BrandEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BrandJson(

    @Json(name = "brand__brand_id")
    val id: Int?,

    @Json(name = "asset__asset_id")
    val assetId: Int?,

    @Json(name = "brand__name")
    val name: String?,

    @Json(name = "asset__cloudinary_url")
    val assetImage: String?
) {
    fun toEntity(pCandidateId: Int): BrandEntity {
        return BrandEntity(
            id.safeValue(),
            assetId.safeValue(),
            name.safeValue(),
            assetImage.safeValue(),
            pCandidateId
        )
    }
}