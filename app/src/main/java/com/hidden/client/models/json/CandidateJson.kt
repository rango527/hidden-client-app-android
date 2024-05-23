package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.helpers.nullable.NullToEmptyString
import com.hidden.client.models.entity.CandidateEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CandidateJson(

    @Json(name = "candidate__candidate_id")
    val id: Int?,

    @Json(name = "asset_candidate__cloudinary_url")
    @NullToEmptyString
    var photo: String?,

    @Json(name = "candidate__first_name")
    val firstName: String?,

    @Json(name = "candidate__surname")
    val lastName: String?,

    @Json(name = "candidate__full_name")
    val fullName: String?
) {
    fun toEntity(): CandidateEntity {
        return CandidateEntity(
            id.safeValue(),
            photo.safeValue(),
            firstName.safeValue(),
            lastName.safeValue(),
            fullName.safeValue(),
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
    }
}
