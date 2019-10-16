package com.hidden.client.datamodels
/*
{
    "candidate__candidate_id": "2",
    "candidate__full_name": "Tanya Walters",
    "candidate__first_name": "Tanya",
    "candidate__surname": "Walters",
    "candidate__email": "malte@hunted.com",
    "job_title_1__name": "Designer",
    "job_title_2__name": "Art Director",
    "job_title_3__name": "UX Designer",
    "asset__cloudinary_url": "https://res.cloudinary.com/dioyg7htb/image/upload/v1540307807/hidden/candidates/2/photo/5bcf3b5f47a6e.jpg",
    "asset_candidate__cloudinary_url": "https://res.cloudinary.com/dioyg7htb/image/upload/v1540307807/hidden/candidates/2/photo/5bcf3b5f47a6e.jpg",
    "candidate__created_at": "2018-07-20T12:44:20+00:00",
    "candidate__last_updated": "2019-06-10T11:14:18+00:00",
    "candidate__hidden_says": "blablabla",
    "candidate__phone": null,
    "candidate__salary_current": "150000.00",
    "candidate__salary_desired": "200000.00",
    "candidate_city__name": "Berkhamsted",
    "candidate__brands": [],
    "candidate__work_experiences": [],
    "candidate__skills": [],
    "candidate__projects": []
}
 */
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
    val candidate__brands: List<HCCandidateResponse>,
    val candidate__work_experiences: List<HCCandidateWorkExperience>,
    val candidate__skills: List<HCCandidateSkillResponse>,
    val candidate__projects: List<HCCandidateProjectResponse>
) {

}