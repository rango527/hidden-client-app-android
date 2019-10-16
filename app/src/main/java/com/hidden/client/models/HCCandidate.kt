package com.hidden.client.models

import android.app.Activity
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.rishabhharit.roundedimageview.RoundedImageView
import de.hdodenhof.circleimageview.CircleImageView

class HCCandidate {

    private var candidateId: Int = 0

    private var candidatePhoto: Int = 0
    private lateinit var candidatePhotoUrl:  String

    private lateinit var candidateFirstName: String
    private lateinit var candidateSurName: String

    private lateinit var candidateTitle: String
    private var candidateJobTitle: Array<String> = arrayOf()

    private lateinit var candidateSnapshot: String

    private var candidateEmployeeHistory: Array<Int> = arrayOf()
    private var candidateSkill:  Array<HCSkill> = arrayOf()

    private var candidateWorkExperience: Array<HCWorkExperience> = arrayOf()

    constructor() { }

    constructor(
        candidateId: Int,
        candidatePhoto: Int,
        candidatePhotoUrl: String,
        candidateFirstName: String,
        candidateSurName: String,
        candidateTitle: String,
        candidateJobTitle: Array<String>,
        candidateSnapshot: String,
        candidateEmployeeHistory: Array<Int>,
        candidateSkill: Array<HCSkill>,
        candidateWorkExperience: Array<HCWorkExperience>
    ) {
        setCandidateId(candidateId)
        setCandidatePhoto(candidatePhoto)
        setCandidatePhotoUrl(candidatePhotoUrl)
        setCandidateFirstName(candidateFirstName)
        setCandidateSurName(candidateSurName)
        setCandidateTitle(candidateTitle)
        setCandidateJobTitle(candidateJobTitle)
        setCandidateSnapshot(candidateSnapshot)
        setCandidateEmployeeHistory(candidateEmployeeHistory)
        setCandidateSkill(candidateSkill)
        setCandidateWorkExperience(candidateWorkExperience)
    }

    companion object {

        lateinit var activity: Activity

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: RoundedImageView, photoUrl: String) {
            Glide.with(activity).load(photoUrl).into(imageView)
        }
    }

    fun getCandiateId(): Int {
        return candidateId
    }

    fun setCandidateId(candidateId: Int) {
        this.candidateId = candidateId
    }

    fun getCandidatePhotoUrl(): String {
        return candidatePhotoUrl
    }

    fun setCandidatePhotoUrl(candidatePhotoUrl: String) {
        this.candidatePhotoUrl = candidatePhotoUrl
    }

    fun getCandidatePhoto(): Int {
        return candidatePhoto
    }

    fun setCandidatePhoto(candidatePhoto: Int) {
        this.candidatePhoto = candidatePhoto
    }

    fun getCandidateFirstName(): String {
        return candidateFirstName
    }

    fun setCandidateFirstName(candidateFirstName: String) {
        this.candidateFirstName = candidateFirstName
    }

    fun getCandidateSurName(): String {
        return candidateSurName
    }

    fun setCandidateSurName(candidateSurName: String) {
        this.candidateSurName = candidateSurName
    }

    fun getCandidateFullName(): String {
        return "$candidateFirstName $candidateSurName"
    }

    fun getCandidateTitle(): String {
        return candidateTitle
    }

    fun setCandidateTitle(candidateTitle: String) {
        this.candidateTitle = candidateTitle
    }

    fun getCandidateJobTitle(): Array<String> {
        return candidateJobTitle
    }

    fun setCandidateJobTitle(candidateJobTitle: Array<String>) {
        this.candidateJobTitle = candidateJobTitle
    }

    fun getCandidateSnapshot(): String {
        return candidateSnapshot
    }

    fun setCandidateSnapshot(candidateSanpshot: String) {
        this.candidateSnapshot = candidateSnapshot
    }

    fun getCandidateEmployeeHistory(): Array<Int> {
        return  candidateEmployeeHistory
    }

    fun setCandidateEmployeeHistory(candidateEmployeeHistory: Array<Int>) {
        this.candidateEmployeeHistory = candidateEmployeeHistory
    }

    fun getCandidateSkill(): Array<HCSkill> {
        return candidateSkill
    }

    fun setCandidateSkill(candidateSkill: Array<HCSkill>) {
        this.candidateSkill = candidateSkill
    }

    fun getCandidateWorkExperience(): Array<HCWorkExperience> {
        return candidateWorkExperience
    }

    fun setCandidateWorkExperience(candidateWorkExperience: Array<HCWorkExperience>) {
        this.candidateWorkExperience = candidateWorkExperience
    }
}