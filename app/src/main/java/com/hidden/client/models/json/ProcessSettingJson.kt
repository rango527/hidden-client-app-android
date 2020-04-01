package com.hidden.client.models.json

import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.JobSettingEntity
import com.hidden.client.models.entity.ProcessSettingEntity
import com.hidden.client.models.entity.ReviewerEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProcessSettingJson(

    @Json(name = "candidate__full_name")
    val candidateFullName: String?,

    @Json(name = "job__title")
    val jobTitle: String?,

    @Json(name = "city__name")
    val cityName: String?,

    @Json(name = "candidate__avatar")
    val candidateAvatar: String?,

    @Json(name = "is_user_manager")
    val isUserManager: Boolean?,

    @Json(name = "user_managers")
    val userManagerList: List<ReviewerJson>?,

    @Json(name = "roles")
    val roles: RoleJson?

) {
    fun toProcessSettingEntity(processId: Int, myId: Int): ProcessSettingEntity {
        return ProcessSettingEntity(
            0,
            candidateFullName.safeValue(),
            jobTitle.safeValue(),
            cityName.safeValue(),
            candidateAvatar.safeValue(),
            isUserManager.safeValue(),
            processId,
            myId
        )
    }

    fun toReviewerList(processId: Int, myId: Int): List<ReviewerEntity> {
        val reviewerList = ArrayList<ReviewerEntity>()

        val interviewerList = roles!!.toInterviewerList(Enums.SettingType.PROCESS.value, processId, myId)
        val interviewAdvancerList = roles.toInterviewAdvancerList(Enums.SettingType.PROCESS.value, processId, myId)
        val offerManagerList = roles.toOfferManagerList(Enums.SettingType.PROCESS.value, processId, myId)

        val userManagerList = ArrayList<ReviewerEntity>()

        for (reviewer in this.userManagerList!!) {
            userManagerList.add(reviewer.toEntity(Enums.ReviewerType.USER_MANAGER.value, Enums.SettingType.PROCESS.value, processId, myId))
        }

        reviewerList.addAll(interviewerList)
        reviewerList.addAll(interviewAdvancerList)
        reviewerList.addAll(offerManagerList)
        reviewerList.addAll(userManagerList)

        return reviewerList
    }
}