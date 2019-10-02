package com.hidden.client.models

class HCJob {

    private lateinit var jobImgURL: String
    private lateinit var jobTitle: String
    private lateinit var jobLocation: String

    private var jobSalaryMin: Int = 0
    private var jobSalaryMax: Int = 0

    private lateinit var jobCompany: String;

    constructor(jobImgURL: String, jobTitle: String, jobLocation: String) {

        this.setJobImgURL(jobImgURL)
        this.setJobTitle(jobTitle)
        this.setJobLocation(jobLocation)
    }

    constructor(jobImgURL: String, jobTitle: String, jobLocation: String, jobSalaryMin: Int, jobSalaryMax: Int, jobCompany: String) {

        this.setJobImgURL(jobImgURL)
        this.setJobTitle(jobTitle)
        this.setJobLocation(jobLocation)
        this.setJobSalaryMin(jobSalaryMin)
        this.setJobSalarayMax(jobSalaryMax)
        this.setJobCompany(jobCompany)
    }

    fun getJobImgURL() : String {
        return jobImgURL
    }

    fun setJobImgURL(jobImgURL: String) {
        this.jobImgURL = jobImgURL
    }

    fun getJobTitle() : String {
        return jobTitle
    }

    fun setJobTitle(jobTitle: String) {
        this.jobTitle = jobTitle
    }

    fun getJobLocation(): String {
        return jobLocation
    }

    fun setJobLocation(jobLocation: String) {
        this.jobLocation = jobLocation
    }

    fun getJobSalaryMin(): Int {
        return jobSalaryMin
    }

    fun setJobSalaryMin(jobSalaryMin: Int) {
        this.jobSalaryMin = jobSalaryMin
    }

    fun getJobSalaryMax(): Int {
        return jobSalaryMax
    }

    fun setJobSalarayMax(jobSalaryMax: Int) {
        this.jobSalaryMax = jobSalaryMax
    }

    fun getJobCompany(): String {
        return jobCompany
    }

    fun setJobCompany(jobCompany: String) {
        this.jobCompany = jobCompany
    }
}