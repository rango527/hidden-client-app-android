package com.hidden.client.datamodels

data class HCCandidateWorkExperience(
    val work_experience__work_experience_id: Int,
    val work_experience__job_title: String,
    val work_experience__description: String,
    val work_experience__worked_from: String,
    val work_experience__worked_to: String,
    val brand__name: String,
    val asset__cloudinary_url: String
)