package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.entity.CandidateEntity

@Dao
interface CandidateDao {

    @get:Query("SELECT * FROM Candidate")
    val all: List<CandidateEntity>

    @Query("SELECT * FROM Candidate WHERE fullName like :search")
    fun getCandidates(search: String): List<CandidateEntity>

    @Query("SELECT * FROM Candidate WHERE id = :id AND email <> ''")
    fun getCandidateById(id: Int): List<CandidateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg candidates: CandidateEntity)

    @Query("DELETE FROM Candidate")
    fun deleteAll()
}