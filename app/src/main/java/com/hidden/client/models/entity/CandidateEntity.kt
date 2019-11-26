package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Candidate")
data class CandidateEntity (

    @field:PrimaryKey
    var candidateId: Int,

    var candidatePhoto: String? = "",

    var candidateFirstName: String,

    var candidateLastName: String,

    var candidateFullName: String,

    /* ------------------------------------------
     * ---- Details -----------------------------
     -------------------------------------------*/

    var candidateEmail: String,

    var jobTitle_1: String,

    var jobTitle_2: String,

    var jobTitle_3: String,

    var createDate: String,

    var lastUpdate: String,

    var hiddenSays: String,

    var candidatePhone: String,

    var salaryCurrent: String,

    var salaryDesired: String,

    var cityName: String
)
