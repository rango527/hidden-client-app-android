package com.hidden.client.models.dao

import androidx.room.*
import com.hidden.client.models.ShortlistEntity
import com.hidden.client.models.entity.CandidateEntity

@Dao
interface ShortlistDao {

    @get:Query("SELECT * FROM Shortlist")
    val all: List<ShortlistEntity>

    @Query("SELECT * FROM Shortlist WHERE entityId = :id")
    fun getShortlistById(id: Int): List<ShortlistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg shortlists: ShortlistEntity)

    @Query("DELETE FROM Shortlist")
    fun deleteAll()
}