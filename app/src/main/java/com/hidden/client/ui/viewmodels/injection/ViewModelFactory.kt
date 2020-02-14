package com.hidden.client.ui.viewmodels.injection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.hidden.client.helpers.APP
import com.hidden.client.models.room.AppDatabase
import com.hidden.client.ui.viewmodels.main.*

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        /* ------------------------------------------------------------
           Dashboard
        -------------------------------------------------------------- */
        if (modelClass.isAssignableFrom(DashboardVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return DashboardVM(db.dashboardTileDao(), db.dashboardTileContentDao()) as T
        }

        if (modelClass.isAssignableFrom(DashboardTileListVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return DashboardTileListVM(db.dashboardTileContentDao()) as T
        }

        /* ------------------------------------------------------------
           Candidate List
        -------------------------------------------------------------- */
        if (modelClass.isAssignableFrom(CandidateListVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return CandidateListVM(db.candidateDao()) as T
        }

        if (modelClass.isAssignableFrom(CandidateDetailVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return CandidateDetailVM(
                context,
                db.candidateDao(), db.brandDao(), db.projectDao(),
                db.skillDao(), db.workExperienceDao()
            ) as T
        }

        /* ------------------------------------------------------------
           Shortlists
        -------------------------------------------------------------- */
        if (modelClass.isAssignableFrom(ShortlistListVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ShortlistListVM(
                db.shortlistDao(),
                db.shortlistCandidateDao(),
                db.feedbackDao(),
                db.feedbackQuestionDao(),
                db.brandDao(),
                db.projectDao(),
                db.projectAssetsDao(),
                db.workExperienceDao(),
                db.skillDao()
            ) as T
        }

        if (modelClass.isAssignableFrom(ShortlistApproveCandidateVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ShortlistApproveCandidateVM(
                db.shortlistCandidateDao()
            ) as T
        }

        if (modelClass.isAssignableFrom(ShortlistRejectCandidateVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ShortlistRejectCandidateVM(
                db.shortlistCandidateDao()
            ) as T
        }

        /* ------------------------------------------------------------
          JobSettingVM
       -------------------------------------------------------------- */
        if (modelClass.isAssignableFrom(JobSettingVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return JobSettingVM(
                context,
                db.jobSettingDao(),
                db.reviewerDao()
            ) as T
        }

        if (modelClass.isAssignableFrom(JobAddUserRoleVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return JobAddUserRoleVM(
                context
            ) as T
        }

        if (modelClass.isAssignableFrom(JobUserManagerVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return JobUserManagerVM(
                db.reviewerDao()
            ) as T
        }

        if (modelClass.isAssignableFrom(JobInviteTeamMemberVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return JobInviteTeamMemberVM(
                context
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}