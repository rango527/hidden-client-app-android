package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.helpers.nullable.*
import com.hidden.client.models.*
import com.hidden.client.models.entity.FeedbackEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShortlistCandidateJson(

    @Json(name = "candidate__candidate_id")
    val id: Int?,

    @Json(name = "process__process_id")
    val processId: Int?,

    @Json(name = "candidate__full_name")
    val fullName: String?,

    @Json(name = "candidate__salary_current")
    val salaryCurrent: String?,

    @Json(name = "candidate__salary_desired")
    val salaryDesired: String?,

    @Json(name = "client__full_name")
    val clientFullName: String?,

    @Json(name = "company__name")
    val companyName: String?,

    @Json(name = "asset_candidate__cloudinary_url")
    val assetUrl: String?,

    @Json(name = "avatar__name")
    val avatarName: String?,

    @Json(name = "avatar__colour")
    val avatarColor: String?,

    @Json(name = "avatar__image")
    val avatarImage: String?,

    @Json(name = "job__job_id")
    val jobId: Int?,

    @Json(name = "job__title")
    val jobTitle: String?,

    @Json(name = "job_title_1__name")
    val jobTitle_1: String?,

    @Json(name = "job_title_2__name")
    val jobTitle_2: String?,

    @Json(name = "job_title_3__name")
    val jobTitle_3: String?,

    @Json(name = "candidate_city__name")
    val cityName: String?,

    @Json(name = "job_city__name")
    val jobCityName: String?,

    @Json(name = "candidate__hidden_says")
    val hiddenSays: String?,

    @Json(name = "candidate__brands")
    val brandList: List<BrandJson>?,

    @Json(name = "candidate__work_experiences")
    val experienceList: List<WorkExperienceJson>?,

    @Json(name = "candidate__projects")
    val projectList: List<ProjectJson>?,

    @Json(name = "candidate__skills")
    val skillList: List<SkillJson>?,

    @Json(name = "job_client__is_submission_reviewer")
    val jobClientIsSubmissionReviewer: Boolean?,

    @Json(name = "job_client__is_interviewer")
    val jobClientIsInterviewer: Boolean?,

    @Json(name = "job_client__is_interview_advancer")
    val jobClientIsInterviewerAdvancer: Boolean?,

    @Json(name = "job_client__is_offer_manager")
    val jobClientIsOfferManager: Boolean?,

    @Json(name = "job_client__is_messenger")
    val jobClientIsMessenger: Boolean?,

    @Json(name = "feedback")
    val feedback: FeedbackJson?
) {
    fun toEntity(pClientId: Int): ShortlistCandidateEntity {
        return ShortlistCandidateEntity(
            id.safeValue(),
            processId.safeValue(),
            fullName.safeValue(),
            salaryCurrent.safeValue(),
            salaryDesired.safeValue(),
            clientFullName.safeValue(),
            companyName.safeValue(),
            assetUrl.safeValue(),
            avatarName.safeValue(),
            avatarColor.safeValue(),
            avatarImage.safeValue(),
            jobId.safeValue(),
            jobTitle.safeValue(),
            jobTitle_1.safeValue(),
            jobTitle_2.safeValue(),
            jobTitle_3.safeValue(),
            cityName.safeValue(),
            jobCityName.safeValue(),
            hiddenSays.safeValue(),
            jobClientIsSubmissionReviewer.safeValue(),
            jobClientIsInterviewer.safeValue(),
            jobClientIsInterviewerAdvancer.safeValue(),
            jobClientIsOfferManager.safeValue(),
            jobClientIsMessenger.safeValue(),
            pClientId
        )
    }

    fun toBrandEntityList(pCandidateId: Int): List<BrandEntity> {
        var brandEntityList: ArrayList<BrandEntity> = arrayListOf()

        for (brand in brandList!!) {
            brandEntityList.add(brand.toEntity(pCandidateId))
        }

        return brandEntityList
    }

    fun toExperienceEntityList(pCandidateId: Int): List<WorkExperienceEntity> {
        var workExperienceEntityList: ArrayList<WorkExperienceEntity> = arrayListOf()

        for (candidateWorkExperience in experienceList!!) {
            workExperienceEntityList.add(candidateWorkExperience.toEntity(pCandidateId))
        }

        return workExperienceEntityList
    }

    fun toProjectEntityList(pCandidateId: Int): List<ProjectEntity> {
        var projectEntityList: ArrayList<ProjectEntity> = arrayListOf()

        for (project in projectList!!) {
            val projectEntity: ProjectEntity = project.toEntity(pCandidateId)
            projectEntity.setAssetsList(project.toAssetsList(project.id.safeValue()))
            projectEntityList.add(projectEntity)
        }

        return projectEntityList
    }

    fun toSkillEntityList(pCandidateId: Int): List<SkillEntity> {
        var skillEntityList: ArrayList<SkillEntity> = arrayListOf()

        for (candidateSkill in skillList!!) {
            skillEntityList.add(candidateSkill.toEntity(pCandidateId))
        }

        return skillEntityList
    }
}