package com.hidden.client.datamodels

data class HCShortlistCandidateResponse (
    val process__process_id: Int,
    val candidate__full_name: String,
    val candidate__salary_current: String,
    val candidate__salary_desired: String,
    val client__full_name: String,
    val company__name: String,
    val asset_candidate__cloudinary_url: String,
    val asset_client__cloudinary_url: String,
    val avatar__name: String,
    val avatar__colour: String,
    val avatar__image: String,
    val job__job_id: Int,
    val job__title: String,
    val job_title_1__name: String,
    val job_title_2__name: String,
    val job_title_3__name: String,
    val candidate_city__name: String,
    val job_city__name: String,
    val candidate__hidden_says: String,
    val candidate__candidate_id: Int,
    val candidate__brands: List<HCCandidateBrandResponse>,
    val candidate__work_experiences: List<HCCandidateWorkExperience>,
    val candidate__skills: List<HCCandidateSkillResponse>,
    val candidate__projects: List<HCCandidateProjectResponse>,
    val feedback: HCFeedbackResponse,
    val job_client__is_submission_reviewer: Boolean,
    val job_client__is_interviewer: Boolean,
    val job_client__is_interview_advancer: Boolean,
    val job_client__is_offer_manager: Boolean,
    val job_client__is_messenger: Boolean
) {

}