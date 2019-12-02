package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.helpers.nullable.NullToZero
import com.hidden.client.models.ProjectEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProjectJson(

    @Json(name = "project__project_id")
    val id: Int?,

    @Json(name = "project__title")
    val title: String?,

    @Json(name = "project__brief")
    val brief: String?,

    @Json(name = "project__activity")
    val activity: String?,

    @Json(name = "brand__name")
    val name: String?,

    @Json(name = "brand_logo__cloudinary_url")
    val image: String?,

    @NullToZero
    var pCandidateId: Int? = 0
) {
    fun toEntity(pCandidateId: Int): ProjectEntity {
        return ProjectEntity(
            id.safeValue(),
            title.safeValue(),
            brief.safeValue(),
            activity.safeValue(),
            name.safeValue(),
            image.safeValue(),
            pCandidateId
        )
    }
}