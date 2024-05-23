package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.BrandEntity
import com.hidden.client.models.entity.ProjectEntity
import com.hidden.client.models.entity.SkillEntity
import com.hidden.client.models.entity.WorkExperienceEntity
import com.hidden.client.models.entity.CandidateEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CandidateDetailJson(

    @Json(name = "candidate__candidate_id")
    val id: Int?,

    @Json(name = "asset_candidate__cloudinary_url")
    val photo: String?,

    @Json(name = "candidate__first_name")
    val firstName: String?,

    @Json(name = "candidate__surname")
    val lastName: String?,

    @Json(name = "candidate__full_name")
    val fullName: String?,

    @Json(name = "candidate__email")
    val email: String?,

    @Json(name = "job_title_1__name")
    val jobTitle_1: String?,

    @Json(name = "job_title_2__name")
    val jobTitle_2: String?,

    @Json(name = "job_title_3__name")
    val jobTitle_3: String?,

    @Json(name = "candidate__created_at")
    val createDate: String?,

    @Json(name = "candidate__last_updated")
    val lastUpdate: String?,

    @Json(name = "candidate__hidden_says")
    val hiddenSays: String?,

    @Json(name = "candidate__phone")
    val phone: String?,

    @Json(name = "candidate__salary_current")
    val salaryCurrent: String?,

    @Json(name = "candidate__salary_desired")
    val salaryDesired: String?,

    @Json(name = "candidate_city__name")
    val cityName: String?,

    @Json(name = "candidate__brands")
    val brandList: List<BrandJson>?,

    @Json(name = "candidate__work_experiences")
    val experienceList: List<WorkExperienceJson>?,

    @Json(name = "candidate__skills")
    val skillList: List<SkillJson>?,

    @Json(name = "candidate__projects")
    val projectList: List<ProjectJson>?
) {
    fun toEntity(): CandidateEntity {
        return CandidateEntity(
            id.safeValue(),
            photo.safeValue(),
            firstName.safeValue(),
            lastName.safeValue(),
            fullName.safeValue(),
            email.safeValue(),
            jobTitle_1.safeValue(),
            jobTitle_2.safeValue(),
            jobTitle_3.safeValue(),
            createDate.safeValue(),
            lastUpdate.safeValue(),
            hiddenSays.safeValue(),
            phone.safeValue(),
            salaryCurrent.safeValue(),
            salaryDesired.safeValue(),
            cityName.safeValue()
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

        for (workExperience in projectList!!) {
            projectEntityList.add(workExperience.toEntity(pCandidateId))
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
