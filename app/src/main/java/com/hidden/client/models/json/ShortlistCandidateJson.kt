package com.hidden.client.models.json

import com.hidden.client.ui.custom.SkillItemView
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShortlistCandidateJson (

    @Json(name = "process__process_id")
    val processId: Int,

    @Json(name = "candidate__full_name")
    val fullName: String,

    @Json(name = "candidate__salary_current")
    val salaryCurrent: String,

    @Json(name = "candidate__salary_desired")
    val salaryDesired: String,

    @Json(name = "client__full_name")
    val clientFullName: String,

    @Json(name = "company__name")
    val companyName: String,

    @Json(name = "asset_candidate__cloudinary_url")
    val assetUrl: String,

    @Json(name = "avatar__name")
    val avatarName: String,

    @Json(name = "avatar__colour")
    val avatarColor: String,

    @Json(name = "avatar__image")
    val avatarImage: String,

    @Json(name = "job__job_id")
    val jobId: Int,

    @Json(name = "job__title")
    val jobTitle: String,

    @Json(name = "job_title_1__name")
    val jobTitle_1: String,

    @Json(name = "job_title_2__name")
    val jobTitle_2: String,

    @Json(name = "job_title_3__name")
    val jobTitle_3: String,

    @Json(name = "candidate_city__name")
    val cityName: String,

    @Json(name = "job_city__name")
    val jobCityName: String,

    @Json(name = "candidate__hidden_says")
    val hiddenSays: String,

    @Json(name = "candidate__candidate_id")
    val candidateId: Int,

    @Json(name = "candidate__skills")
    val skillList: List<CandidateSkillJson>,

    @Json(name = "job_client__is_submission_reviewer")
    val jobClientIsSubmissionReviewer: Boolean,

    @Json(name = "job_client__is_interviewer")
    val jobClientIsInterviewer: Boolean,

    @Json(name = "job_client__is_interview_advancer")
    val jobClientIsInterviewerAdvancer: Boolean,

    @Json(name = "job_client__is_offer_manager")
    val jobClientIsOfferManager: Boolean,

    @Json(name = "job_client__is_messenger")
    val jobClientIsMessenger: Boolean,

    @Json(name = "feedback")
    val feedback: List<FeedbackJson>
)