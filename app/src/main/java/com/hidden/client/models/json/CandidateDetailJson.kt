package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CandidateDetailJson (

    @Json(name = "candidate__candidate_id")
    val candidateId: Int,

    @Json(name = "asset_candidate__cloudinary_url")
    val candidatePhoto: String,

    @Json(name = "candidate__first_name")
    val candidateFirstName: String,

    @Json(name = "candidate__surname")
    val candidateLastName: String,

    @Json(name = "candidate__full_name")
    val candidateFullName: String,

    @Json(name = "candidate__email")
    val candidateEmail: String,

    @Json(name = "job_title_1__name")
    val jobTitle_1: String,

    @Json(name = "job_title_2__name")
    val jobTitle_2: String,

    @Json(name = "job_title_3__name")
    val jobTitle_3: String,

    @Json(name = "candidate__created_at")
    val createDate: String,

    @Json(name = "candidate__last_updated")
    val lastUpdate: String,

    @Json(name = "candidate__hidden_says")
    val hiddenSays: String,

    @Json(name = "candidate__phone")
    val candidatePhone: String,

    @Json(name = "candidate__salary_current")
    val salaryCurrent: String,

    @Json(name = "candidate__salary_desired")
    val salaryDesired: String,

    @Json(name = "candidate_city__name")
    val cityName: String,

    @Json(name = "candidate__brands")
    val brands: List<CandidateBrandJson>,

    @Json(name = "candidate__work_experiences")
    val workExperiences: List<CandidateWorkExperienceJson>,

    @Json(name = "candidate__skills")
    val skills: List<CandidateSkillJson>,

    @Json(name = "candidate__projects")
    val projects: List<CandidateProjectJson>
) {

}
