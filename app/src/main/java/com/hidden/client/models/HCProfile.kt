package com.hidden.client.models

class HCProfile {

    private lateinit var title: String
    private lateinit var location: String

    private lateinit var jobTitles: Array<String>

    private lateinit var feedback: String

    private lateinit var employeeHistory: Array<String>

    private lateinit var projects: Array<String>

    private lateinit var skills: Array<HCSkill>

    constructor() {

    }

    constructor(
        title: String,
        location: String,
        jobTitles: Array<String>,
        feedback: String,
        employeeHistory: Array<String>,
        projects: Array<String>,
        skills: Array<HCSkill>
    ) {
        setTitle(title)
        setLocation(location)
        setJobTitles(jobTitles)
        setFeedback(feedback)
        setEmployeeHistory(employeeHistory)
        setProjects(projects)
        setSkills(skills)
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