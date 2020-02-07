package com.hidden.client.models.json

import com.hidden.client.helpers.Enums
import com.hidden.client.models.entity.ReviewerEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoleJson(

    @Json(name = "interviewer")
    val interviewerList: List<ReviewerJson>?,

    @Json(name = "submission_reviewer")
    val shortlistReviewerList: List<ReviewerJson>?,

    @Json(name = "interview_advancer")
    val interviewAdvancerList: List<ReviewerJson>?,

    @Json(name = "offer_manager")
    val offerManagerList: List<ReviewerJson>?

) {
    fun toInterviewerList(myId: Int): List<ReviewerEntity> {
        val reviewerEntityList: ArrayList<ReviewerEntity> = arrayListOf()

        for (reviewer in this.interviewerList!!) {
            reviewerEntityList.add(reviewer.toEntity(Enums.ReviewerType.INTERVIEWER.value, myId))
        }
        return reviewerEntityList
    }

    fun toShortlistReviewerList(myId: Int): List<ReviewerEntity> {
        val reviewerEntityList: ArrayList<ReviewerEntity> = arrayListOf()

        for (reviewer in this.shortlistReviewerList!!) {
            reviewerEntityList.add(reviewer.toEntity(Enums.ReviewerType.SHORTLIST_REVIEWER.value, myId))
        }
        return reviewerEntityList
    }

    fun toInterviewAdvancerList(myId: Int): List<ReviewerEntity> {
        val reviewerEntityList: ArrayList<ReviewerEntity> = arrayListOf()

        for (reviewer in this.interviewAdvancerList!!) {
            reviewerEntityList.add(reviewer.toEntity(Enums.ReviewerType.INTERVIEWER_ADVANCER.value, myId))
        }
        return reviewerEntityList
    }

    fun toOfferManagerList(myId: Int): List<ReviewerEntity> {
        val reviewerEntityList: ArrayList<ReviewerEntity> = arrayListOf()

        for (reviewer in this.offerManagerList!!) {
            reviewerEntityList.add(reviewer.toEntity(Enums.ReviewerType.OFFER_MANAGER.value, myId))
        }
        return reviewerEntityList
    }
}