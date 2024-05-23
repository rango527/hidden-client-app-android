package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "ShortlistCandidate")
data class ShortlistCandidateEntity (

    @field:PrimaryKey
    val candidateId: Int,

    val processId: Int,

    val fullName: String,

    val salaryCurrent: String,

    val salaryDesired: String,

    val clientFullName: String,

    val companyName: String,

    val assetUrl: String,

    val avatarName: String,

    val avatarColor: String,

    val avatarImage: String,

    val jobId: Int,

    val jobTitle: String,

    val jobTitle_1: String,

    val jobTitle_2: String,

    val jobTitle_3: String,

    val cityName: String,

    val jobCityName: String,

    val hiddenSays: String,

    val jobClientIsSubmissionReviewer: Boolean,

    val jobClientIsInterviewer: Boolean,

    val jobClientIsInterviewerAdvancer: Boolean,

    val jobClientIsOfferManager: Boolean,

    val jobClientIsMessenger: Boolean,

    val pClientId: Int
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