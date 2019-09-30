package com.hidden.client.models

class HCJob {

    private lateinit var jobImgURL: String
    private lateinit var jobTitle: String
    private lateinit var jobLocation: String

    constructor(jobImgURL: String, jobTitle: String, jobLocation: String) {

        this.setJobImgURL(jobImgURL)
        this.setJobTitle(jobTitle)
        this.setJobLocation(jobLocation)
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
}