package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.helpers.nullable.NullToZero
import com.hidden.client.models.ProjectAssetsEntity
import com.hidden.client.models.ProjectEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProjectAssetsJson(

    @Json(name = "project_asset__asset_id")
    val id: Int?,

    @Json(name = "project_asset__asset_type")
    val type: String?,

    @Json(name = "project_asset__cloudinary_url")
    val url: String?,

    @Json(name = "project_asset__is_main_image")
    val mainImage: String?,

    @NullToZero
    var pProjectId: Int? = 0
) {
    fun toEntity(pProjectId: Int): ProjectAssetsEntity {
        return ProjectAssetsEntity(
            id.safeValue(),
            type.safeValue(),
            url.safeValue(),
            mainImage.safeValue(),
            pProjectId
        )
    }
}