package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.Candidate

@Dao
interface CandidateDao {
    @get:Query("SELECT * FROM Candidate")
    val all: List<Candidate>

    @Query("SELECT * FROM Candidate WHERE candidateFullName like :search")
    fun getCandidates(search: String): List<Candidate>

    @Insert
    fun insertAll(vararg candidates: Candidate)

    @Query("DELETE FROM Candidate")
    fun deleteAll()
}