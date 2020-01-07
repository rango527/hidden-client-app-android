package com.hidden.client.models_

import androidx.databinding.BindingAdapter
import de.hdodenhof.circleimageview.CircleImageView

class HCProcess(
    candidatePhoto: Int,
    candidateName: String,
    candidateJob: String,
    candidateLocation: String,
    processStage: Int,
    processStatus: Int
) {

    private var candidatePhoto: Int = 0
    private lateinit var candidateName: String
    private lateinit var candidateJob: String
    private lateinit var candidateLocation: String

    private var processStage: Int = 0

    private var processStatus: Int = 0

    init {
        this.setCandidatePhoto(candidatePhoto)
        this.setCandidateName(candidateName)
        this.setCandidateJob(candidateJob)
        this.setCandidateLocation(candidateLocation)
        this.setProcessStage(processStage)
        this.setProcessStatus(processStatus)
    }

    companion object {
        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: CircleImageView, resource: Int) {
            imageView.setImageResource(resource)
        }
    }

    fun getCandidatePhoto(): Int {
        return candidatePhoto
    }

    fun setCandidatePhoto(candidatePhoto: Int) {
        this.candidatePhoto = candidatePhoto
    }

    fun getCandidateName(): String {
        return candidateName
    }

    fun setCandidateName(candidateName: String) {
        this.candidateName = candidateName
    }

    fun getCandidateJob(): String {
        return candidateJob
    }

    fun setCandidateJob(candidateJob: String) {
        this.candidateJob = candidateJob
    }

    fun getCandidateLocation(): String {
        return candidateLocation
    }

    fun setCandidateLocation(candidateLocation: String) {
        this.candidateLocation = candidateLocation
    }

    fun getProcessStage(): Int {
        return processStage
    }

    fun setProcessStage(processStage: Int) {
        this.processStage = processStage
    }

    fun getProcessStatus(): Int {
        return processStatus
    }

    fun setProcessStatus(processStatus: Int) {
        this.processStatus = processStatus
    }
}