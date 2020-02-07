package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.BrandEntity
import com.hidden.client.models.entity.JobSettingEntity
import com.hidden.client.models.entity.ReviewerEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JobSettingJson(

    @Json(name = "review_type")
    val reviewType: String?,

    @Json(name = "is_user_manager")
    val isUserManager: Boolean?,

    @Json(name = "user_managers")
    val userManagerList: List<ReviewerJson>?,

    @Json(name = "roles")
    val roles: RoleJson?

) {
    fun toJobSettingEntity(myId: Int): JobSettingEntity {
        return JobSettingEntity(
            0,
            reviewType.safeValue(),
            isUserManager.safeValue(),
            myId
        )
    }

    fun toReviewerList(myId: Int): List<ReviewerEntity> {
        val reviewerList = ArrayList<ReviewerEntity>()

        val interviewerList = roles!!.toInterviewerList(myId)
        val shortlistInterviewerList = roles!!.toShortlistReviewerList(myId)
        val interviewAdvancerList = roles!!.toInterviewAdvancerList(myId)
        val offerManagerList = roles.toOfferManagerList(myId)

        reviewerList.addAll(interviewerList)
        reviewerList.addAll(shortlistInterviewerList)
        reviewerList.addAll(interviewAdvancerList)
        reviewerList.addAll(offerManagerList)

        return reviewerList
    }
}