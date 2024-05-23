package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Shortlist")
data class ShortlistEntity (

    @field:PrimaryKey
    val clientId: Int,

    val clientFullName: String,

    val clientUrl: String
) {
    @Ignore
    private var candidateList: List<ShortlistCandidateEntity> = listOf()

    fun getCandidateList(): List<ShortlistCandidateEntity> {
        return candidateList
    }

    fun setCandidateList(candidateList: List<ShortlistCandidateEntity>) {
        this.candidateList = candidateList
    }
}