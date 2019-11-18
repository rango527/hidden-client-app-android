package com.hidden.client.models.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hidden.client.helpers.APP
import com.hidden.client.models.Candidate
import com.hidden.client.models.dao.CandidateDao


@Database(entities = [Candidate::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun candidateDao(): CandidateDao

    companion object {

        /**
         * The only instance
         */
        private var sInstance: AppDatabase? = null

        /**
         * Gets the singleton instance of SampleDatabase.
         *
         * @param context The context.
         * @return The singleton instance of SampleDatabase.
         */
        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room
                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, APP.database)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return sInstance!!
        }
    }
}