package com.hidden.client.ui.viewmodels.injection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.hidden.client.helpers.APP
import com.hidden.client.models.room.AppDatabase
import com.hidden.client.models_.HCBrand
import com.hidden.client.models_.HCJobDetailTile
import com.hidden.client.models_.HCWorkExperience
import com.hidden.client.ui.viewmodels.intro.EditProfileVM
import com.hidden.client.ui.viewmodels.intro.LoginVM
import com.hidden.client.ui.viewmodels.intro.SignUpVM
import com.hidden.client.ui.viewmodels.intro.ResetPasswordVM
import com.hidden.client.ui.viewmodels.main.*
import com.hidden.client.ui.viewmodels___.HCBrandViewModel
import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel
import com.hidden.client.ui.viewmodels___.HCJobDetailTileViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return LoginVM(
                context
            ) as T
        }
        if (modelClass.isAssignableFrom(EditProfileVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return EditProfileVM(
                context
            ) as T
        }
        if (modelClass.isAssignableFrom(SignUpVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return SignUpVM(
                context
            ) as T
        }
        if (modelClass.isAssignableFrom(ResetPasswordVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ResetPasswordVM(
                context
            ) as T
        }
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
            return DashboardVM(
                db.brandDao(),
                db.candidateDao(),
                db.conversationDao(),
                db.dashboardTileContentDao(),
                db.dashboardTileDao(),
                db.feedbackDao(),
                db.feedbackIdDao(),
                db.feedbackQuestionDao(),
                db.interviewParticipantDao(),
                db.jobSettingDao(),
                db.messageListDao(),
                db.processDao(),
                db.processSettingDao(),
                db.projectAssetsDao(),
                db.processStageDao(),
                db.projectDao(),
                db.reviewerDao(),
                db.shortlistCandidateDao(),
                db.shortlistDao(),
                db.skillDao(),
                db.timelineDao(),
                db.workExperienceDao()
            ) as T
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

        if (modelClass.isAssignableFrom(HCBrandViewModel::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return HCBrandViewModel(
                HCBrand(brandPhotoUrl = "")
            ) as T
        }

        if (modelClass.isAssignableFrom(HCWorkExperienceViewModel::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return HCWorkExperienceViewModel(
                HCWorkExperience()
            ) as T
        }

        if (modelClass.isAssignableFrom(HCJobDetailTileViewModel::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return HCJobDetailTileViewModel(
                HCJobDetailTile()
            ) as T
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
                context,
                db.shortlistDao(),
                db.shortlistCandidateDao(),
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

        if (modelClass.isAssignableFrom(ShortlistFeedbackVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ShortlistFeedbackVM(
                context
            ) as T
        }

        if (modelClass.isAssignableFrom(InterviewDetailVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return InterviewDetailVM(
                context
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
        /* ------------------------------------------------------------
          JobList VM
       -------------------------------------------------------------- */
        if (modelClass.isAssignableFrom(JobListVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return JobListVM(
                db.processDao(),
                db.processStageDao()
            ) as T
        }

        /* ------------------------------------------------------------
          Process VM
       -------------------------------------------------------------- */
        if (modelClass.isAssignableFrom(ProcessListVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ProcessListVM(
                context,
                db.processDao(),
                db.processStageDao()
            ) as T
        }

        if (modelClass.isAssignableFrom(ProcessDetailVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ProcessDetailVM(
                context,
                db.processDao(),
                db.processStageDao(),
                db.timelineDao(),
                db.interviewParticipantDao(),
                db.feedbackIdDao()
            ) as T
        }

        if (modelClass.isAssignableFrom(ProcessTabVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ProcessTabVM(
                context
            ) as T
        }

        /* ------------------------------------------------------------
         Message VM
      -------------------------------------------------------------- */
        if (modelClass.isAssignableFrom(MessageListVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return MessageListVM(
                context,
                db.conversationDao(),
                db.messageListDao()
            ) as T
        }

        if (modelClass.isAssignableFrom(MessageViewVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return MessageViewVM(
//                db.messageListDao()
            ) as T
        }

        /* ------------------------------------------------------------
          ProcessSettingVM
       -------------------------------------------------------------- */
        if (modelClass.isAssignableFrom(ProcessSettingVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ProcessSettingVM(
                context,
                db.processSettingDao(),
                db.reviewerDao()
            ) as T
        }

        if (modelClass.isAssignableFrom(ProcessUserManagerVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ProcessUserManagerVM(
                db.reviewerDao()
            ) as T
        }

        if (modelClass.isAssignableFrom(ProcessAddUserRoleVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ProcessAddUserRoleVM(
                context
            ) as T
        }

        /**
         * Feedback
         */
        if (modelClass.isAssignableFrom(FeedbackVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return FeedbackVM(
                context
            ) as T
        }

        /**
         * GiveFeedback
         */
        if (modelClass.isAssignableFrom(GiveFeedbackVM::class.java)) {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return GiveFeedbackVM(
                context
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}