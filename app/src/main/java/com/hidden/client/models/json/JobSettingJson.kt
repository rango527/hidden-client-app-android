package com.hidden.client.models.json

import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.extension.safeValue
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

    @Json(name = "city__name")
    val cityName: String?,

    @Json(name = "job__title")
    val jobTitle: String?,

    @Json(name = "user_managers")
    val userManagerList: List<ReviewerJson>?,

    @Json(name = "roles")
    val roles: RoleJson?

) {
    fun toJobSettingEntity(jobId:Int, myId: Int): JobSettingEntity {
        return JobSettingEntity(
            0,
            reviewType.safeValue(),
            cityName.safeValue(),
            jobTitle.safeValue(),
            isUserManager.safeValue(),
            jobId,
            myId
        )
    }

    fun toReviewerList(jobId:Int, myId: Int): List<ReviewerEntity> {
        val reviewerList = ArrayList<ReviewerEntity>()

        val interviewerList = roles!!.toInterviewerList(Enums.SettingType.JOB.value, jobId, myId)
        val shortlistInterviewerList = roles.toShortlistReviewerList(Enums.SettingType.JOB.value, jobId, myId)
        val interviewAdvancerList = roles.toInterviewAdvancerList(Enums.SettingType.JOB.value, jobId, myId)
        val offerManagerList = roles.toOfferManagerList(Enums.SettingType.JOB.value, jobId, myId)

        val userManagerList = ArrayList<ReviewerEntity>()

        for (reviewer in this.userManagerList!!) {
            userManagerList.add(reviewer.toEntity(Enums.ReviewerType.USER_MANAGER.value, Enums.SettingType.JOB.value, jobId, myId))
        }

        reviewerList.addAll(interviewerList)
        reviewerList.addAll(shortlistInterviewerList)
        reviewerList.addAll(interviewAdvancerList)
        reviewerList.addAll(offerManagerList)
        reviewerList.addAll(userManagerList)

        return reviewerList
    }
}