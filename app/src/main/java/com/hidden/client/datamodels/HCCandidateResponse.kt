package com.hidden.client.datamodels

/*
{
    "candidate__candidate_id": "54",
    "asset_candidate__cloudinary_url": "https://res.cloudinary.com/dioyg7htb/image/upload/v1561136398/hidden/candidates/54/photo/5d0d0d0e1a9c7.jpg",
    "candidate__first_name": "Guy",
    "candidate__surname": "Tam",
    "candidate__full_name": "Guy Tam"
}
 */

data class HCCandidateResponse(
    val candidate__candidate_id: Int,
    val asset_candidate__cloudinary_url: String,
    val candidate__first_name: String,
    val candidate__surname: String,
    val candidate__full_name: String
) {

}