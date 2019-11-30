package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShortlistCandidate")
data class ShortlistCandidateEntity (

    @PrimaryKey(autoGenerate = true)
    val entityId: Int,

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

    val candidateId: Int,

    val jobClientIsSubmissionReviewer: Boolean,

    val jobClientIsInterviewer: Boolean,

    val jobClientIsInterviewerAdvancer: Boolean,

    val jobClientIsOfferManager: Boolean,

    val jobClientIsMessenger: Boolean,

    val pShortlistId: Int
)