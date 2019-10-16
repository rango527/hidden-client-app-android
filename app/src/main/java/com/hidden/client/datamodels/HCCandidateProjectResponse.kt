package com.hidden.client.datamodels

/*
{
    "project__project_id": "5",
    "project__title": "Designer",
    "project__brief": "333",
    "project__activity": "sidl;ksndf . lijflsdj l",
    "brand__name": "Animals",
    "brand_logo__cloudinary_url": "https://res.cloudinary.com/dioyg7htb/image/upload/v1532537444/hidden/brands/736/logo/5b58aa62b5962.jpg",
    "candidate__project_assets": []
}
 */
data class HCCandidateProjectResponse(
    val project__project_id: Int,
    val project__title: String,
    val project__brief: String,
    val project__activity: String,
    val brand__name: String,
    val brand_logo__cloudinary_url: String
) {
}