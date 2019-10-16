package com.hidden.client.datamodels

/*
{
    "skill__skill_id": "8",
    "skill__name": "UX Design",
    "candidate_skill__ranking": "4"
}
 */
data class HCCandidateSkillResponse(
    val skill__skill_id: Int,
    val skill__name: String,
    val candidate_skill__ranking: Int
) {

}