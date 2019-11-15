package com.hidden.client.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hidden.client.models.Candidate
import com.hidden.client.models.dao.CandidateDao


@Database(entities = [Candidate::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun candidateDao(): CandidateDao
}