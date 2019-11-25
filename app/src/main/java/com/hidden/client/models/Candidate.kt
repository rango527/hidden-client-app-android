package com.hidden.client.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hidden.client.helpers.nullable.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Candidate (

    @field:PrimaryKey
    @Json(name = "candidate__candidate_id")
    var candidateId: Int = 0,

    @Json(name = "asset_candidate__cloudinary_url")
    var candidatePhoto: String? = "",

    @Json(name = "candidate__first_name")
    var candidateFirstName: String,

    @Json(name = "candidate__surname")
    var candidateLastName: String,

    @Json(name = "candidate__full_name")
    var candidateFullName: String,

    // Details Info
    @NullToEmptyString
    @Json(name = "candidate__email")
    var candidateEmail: String = "",

    @NullToEmptyString
    @Json(name = "job_title_1__name")
    var jobTitle_1: String = "",

    @NullToEmptyString
    @Json(name = "job_title_2__name")
    var jobTitle_2: String = "",

    @NullToEmptyString
    @Json(name = "job_title_3__name")
    var jobTitle_3: String = "",

    @NullToEmptyString
    @Json(name = "candidate__created_at")
    var createDate: String = "",

    @NullToEmptyString
    @Json(name = "candidate__last_updated")
    var lastUpdate: String = "",

    @NullToEmptyString
    @Json(name = "candidate__hidden_says")
    var hiddenSays: String = "",

    @NullToEmptyString
    @Json(name = "candidate__phone")
    var candidatePhone: String = "",

    @NullToEmptyString
    @Json(name = "candidate__salary_current")
    var salaryCurrent: String = "",

    @NullToEmptyString
    @Json(name = "candidate__salary_desired")
    var salaryDesired: String = "",

    @NullToEmptyString
    @Json(name = "candidate_city__name")
    var cityName: String = "",

    @Json(name = "candidate__brands")
    @Ignore
    @NullableCandidateBrand
    var brands: List<CandidateBrand> = listOf(),

    @Json(name = "candidate__work_experiences")
    @Ignore
    @NullableCandidateWorkExperience
    var workExperiences: List<CandidateWorkExperience> = listOf(),

    @Json(name = "candidate__skills")
    @Ignore
    @NullableCandidateSkill
    var skills: List<CandidateSkill> = listOf(),

    @Json(name = "candidate__projects")
    @Ignore
    @NullableCandidateProject
    var projects: List<CandidateProject> = listOf()
) {
    constructor() : this(
        0,
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
        "",
        "",
        "",
        "",
        "",
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList()
    )
}
