package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ProjectAssetsEntity
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
    val mainImage: Boolean?
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