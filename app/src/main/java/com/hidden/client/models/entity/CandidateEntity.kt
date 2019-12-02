package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hidden.client.models.*

@Entity(tableName = "Candidate")
data class CandidateEntity(

    @field:PrimaryKey
    var id: Int,

    var photo: String? = "",

    var firstName: String,

    var lastName: String,

    var fullName: String,

    /* ------------------------------------------
     * ---- Details -----------------------------
     -------------------------------------------*/

    var email: String,

    var jobTitle_1: String,

    var jobTitle_2: String,

    var jobTitle_3: String,

    var createDate: String,

    var lastUpdate: String,

    var hiddenSays: String,

    var phone: String,

    var salaryCurrent: String,

    var salaryDesired: String,

    var cityName: String
) {
    @Ignore
    private var brandList: List<BrandEntity> = listOf()

    @Ignore
    private var projectList: List<ProjectEntity> = listOf()

    @Ignore
    private var skillList: List<SkillEntity> = listOf()

    @Ignore
    private var experienceList: List<WorkExperienceEntity> = listOf()

    @Ignore
    private var feedbackList: List<FeedbackEntity> = listOf()

    fun getBrandList(): List<BrandEntity> {
        return brandList
    }

    fun setBrandList(brandList: List<BrandEntity>) {
        this.brandList = brandList
    }

    fun getProjectList(): List<ProjectEntity> {
        return projectList
    }

    fun setProjectList(projectList: List<ProjectEntity>) {
        this.projectList = projectList
    }

    fun getSkillList(): List<SkillEntity> {
        return skillList
    }

    fun setSkillList(skillList: List<SkillEntity>) {
        this.skillList = skillList
    }

    fun getExperienceList(): List<WorkExperienceEntity> {
        return experienceList
    }

    fun setExperienceList(experienceList: List<WorkExperienceEntity>) {
        this.experienceList = experienceList
    }

    fun getFeedbackList(): List<FeedbackEntity> {
        return feedbackList
    }

    fun setFeedbackList(feedbackList: List<FeedbackEntity>) {
        this.feedbackList = feedbackList
    }
}