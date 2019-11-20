package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hidden.client.helpers.nullable.NullToEmptyString
import com.hidden.client.helpers.nullable.NullToZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class CandidateBrand (

    @field:PrimaryKey
    @Json(name = "brand__brand_id")
    val brandId: Int,

    @Json(name = "asset__asset_id")
    val assetId: Int,

    @Json(name = "brand__name")
    val brandName: Int,

    @Json(name = "asset__cloudinary_url")
    val assetImage: Int,

    @NullToZero
    var pCandidateId: Int = 0

)