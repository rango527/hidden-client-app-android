package com.hidden.client.models_

class HCProfile {

    private var photo: Int = 0

    private lateinit var title: String
    private lateinit var location: String

    private var jobTitles: Array<String> = arrayOf()

    private lateinit var feedback: String

    private var employeeHistory: Array<String> = arrayOf()

    private var projects: Array<String> = arrayOf()

    private var skills: Array<HCSkill> = arrayOf()

    fun getJobTitleWithSeparator() : String {
        var ret: String = ""

        for ((index, value) in this.jobTitles.withIndex()) {

            if (index != 0) {
                ret += " | "
            }

            ret += value;
        }

        return ret;
    }

    constructor() {

    }

    constructor(
        photo: Int,
        title: String,
        location: String,
        jobTitles: Array<String>,
        feedback: String,
        employeeHistory: Array<String>,
        projects: Array<String>,
        skills: Array<HCSkill>
    ) {
        setPhoto(photo)
        setTitle(title)
        setLocation(location)
        setJobTitles(jobTitles)
        setFeedback(feedback)
        setEmployeeHistory(employeeHistory)
        setProjects(projects)
        setSkills(skills)
    }

    fun getPhoto(): Int {
        return photo
    }

    fun setPhoto(photo: Int) {
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
}