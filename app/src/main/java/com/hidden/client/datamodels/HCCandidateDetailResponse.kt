package com.hidden.client.datamodels

data class HCCandidateDetailResponse (
    val candidate__candidate_id: Int,
    val candidate__full_name: String,
    val candidate__first_name: String,
    val candidate__surname: String,
    val candidate__email: String,
    val job_title_1__name: String,
    val job_title_2__name: String,
    val job_title_3__name: String,
    val asset__cloudinary_url: String,
    val asset_candidate__cloudinary_url: String,
    val candidate__created_at: String,
    val candidate__last_updated: String,
    val candidate__hidden_says: String,
    val candidate__phone: String,
    val candidate__salary_current: Float,
    val candidate__salary_desired: Float,
    val candidate_city__name: String,
    val candidate__brands: List<HCCandidateBrandResponse>,
    val candidate__work_experiences: List<HCCandidateWorkExperience>,
    val candidate__skills: List<HCCandidateSkillResponse>,
    val candidate__projects: List<HCCandidateProjectResponse>
) {

}