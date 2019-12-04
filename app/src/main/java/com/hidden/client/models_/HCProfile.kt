package com.hidden.client.models_

import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel

class HCProfile {

    private var id: Int = 0;

    private var photo: String = ""

    private lateinit var title: String
    private lateinit var location: String

    private var jobTitles: Array<String> = arrayOf()

    private lateinit var feedback: String

    private var employeeHistory: Array<String> = arrayOf()

    private var projects: Array<String> = arrayOf()

    private lateinit var workExperience: HCWorkExperienceViewModel

    private var skills: Array<HCSkill> = arrayOf()

    fun getJobTitleWithSeparator() : String {
        var ret: String = ""

        var index = 0
        for (value in this.jobTitles) {

            if (value === null) continue;
            if (value === "") continue;

            if (index != 0) {
                ret += " | "
            }

            index++
            ret += value;
        }

        return ret;
    }

    constructor() {

    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getPhoto(): String {
        return photo
    }

    fun setPhoto(photo: String) {
        this.photo = photo
    }

    fun getTitle() : String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getLocation() : String {
        return location
    }

    fun setLocation(location: String) {
        this.location = location
    }

    fun getJobTitles() : Array<String> {
        return jobTitles
    }

    fun setJobTitles(jobTitles: Array<String>) {
        this.jobTitles = jobTitles
    }

    fun getFeedback() : String {
        return feedback
    }

    fun setFeedback(feedback : String) {
        this.feedback = feedback
    }

    fun getEmployeeHistory() : Array<String> {
        return employeeHistory
    }

    fun setEmployeeHistory(employeeHistory: Array<String>) {
        this.employeeHistory = employeeHistory
    }

    fun getProjects() : Array<String> {
        return projects
    }

    fun setProjects(projects: Array<String>) {
        this.projects = projects
    }

    fun getSkills() : Array<HCSkill> {
        return skills
    }

    fun setSkills(skills: Array<HCSkill>) {
        this.skills = skills
    }

    fun getWorkExperience(): HCWorkExperienceViewModel {
        return workExperience
    }

    fun setWorkExperience(workExperience:HCWorkExperienceViewModel) {
        this.workExperience = workExperience
    }
}