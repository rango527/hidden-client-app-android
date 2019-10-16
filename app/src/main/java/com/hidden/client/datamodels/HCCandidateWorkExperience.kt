package com.hidden.client.datamodels

/*
{
    "work_experience__work_experience_id": "736",
    "work_experience__job_title": "Designer",
    "work_experience__description": null,
    "work_experience__worked_from": "2018-07-16T00:00:00+00:00",
    "work_experience__worked_to": "2018-08-30T00:00:00+00:00",
    "brand__name": "Animals",
    "asset__cloudinary_url": "https://res.cloudinary.com/dioyg7htb/image/upload/v1532537444/hidden/brands/736/logo/5b58aa62b5962.jpg"
}
 */
data class HCCandidateWorkExperience(
    val work_experience__work_experience_id: Int,
    val work_experience__job_title: String,
    val work_experience__description: String,
    val work_experience__worked_from: String,
    val work_experience__worked_to: String,
    val brand__name: String,
    val asset__cloudinary_url: String
) {
}