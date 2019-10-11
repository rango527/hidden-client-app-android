package com.hidden.client.models

class HCCandidate {

    private var candidatePhoto: Int = 0

    private lateinit var candidateTitle: String
    private var candidateJobTitle: Array<String> = arrayOf()

    private lateinit var candidateSnapshot: String

    private var candidateEmployeeHistory: Array<Int> = arrayOf()
    private var candidateSkill:  Array<HCSkill> = arrayOf()

    private var candidateWorkExperience: Array<HCWorkExperience> = arrayOf()

    constructor() {

    }

    constructor(
        candidatePhoto: Int,
        candidateTitle: String,
        candidateJobTitle: Array<String>,
        candidateSnapshot: String,
        candidateEmployeeHistory: Array<Int>,
        candidateSkill: Array<HCSkill>,
        candidateWorkExperience: Array<HCWorkExperience>
    ) {
        setCandidatePhoto(candidatePhoto)
        setCandidateTitle(candidateTitle)
        setCandidateJobTitle(candidateJobTitle)
        setCandidateSnapshot(candidateSnapshot)
        setCandidateEmployeeHistory(candidateEmployeeHistory)
        setCandidateSkill(candidateSkill)
        setCandidateWorkExperience(candidateWorkExperience)
    }


    fun getCandidatePhoto(): Int {
        return candidatePhoto
    }

    fun setCandidatePhoto(candidatePhoto: Int) {
        this.candidatePhoto = candidatePhoto
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