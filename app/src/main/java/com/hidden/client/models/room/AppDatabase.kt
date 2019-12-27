package com.hidden.client.models.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hidden.client.helpers.APP
import com.hidden.client.models.*
import com.hidden.client.models.dao.*
import com.hidden.client.models.entity.CandidateEntity
import com.hidden.client.models.entity.FeedbackEntity
import com.hidden.client.models.entity.FeedbackQuestionEntity

@Database(entities = [
    CandidateEntity::class,
    BrandEntity::class,
    ProjectEntity::class,
    ProjectAssetsEntity::class,
    SkillEntity::class,
    WorkExperienceEntity::class,
    ShortlistEntity::class,
    ShortlistCandidateEntity::class,
    FeedbackEntity::class,
    FeedbackQuestionEntity::class,
    DashboardTileEntity::class,
    DashboardTileContentEntity::class
], version = APP.databaseVersion)
abstract class AppDatabase : RoomDatabase() {

    abstract fun candidateDao(): CandidateDao
    abstract fun brandDao(): BrandDao

    abstract fun projectDao(): ProjectDao
    abstract fun projectAssetsDao(): ProjectAssetsDao

    abstract fun skillDao(): SkillDao
    abstract fun workExperienceDao(): WorkExperienceDao

    abstract fun feedbackDao(): FeedbackDao
    abstract fun feedbackQuestionDao(): FeedbackQuestionDao

    // Shortlist
    abstract fun shortlistDao(): ShortlistDao
    abstract fun shortlistCandidateDao(): ShortlistCandidateDao

    // Dashboard
    abstract fun dashboardTileDao(): DashboardTileDao
    abstract fun dashboardTileContentDao(): DashboardTileContentDao

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