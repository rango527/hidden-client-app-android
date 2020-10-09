package com.hidden.client.ui.viewmodels.injection.component

import com.hidden.client.ui.viewmodels.custom.DashboardNumberTileViewVM
import com.hidden.client.ui.viewmodels.custom.DashboardPhotoTileViewVM
import dagger.Component
import com.hidden.client.ui.viewmodels.injection.module.NetworkModule
import com.hidden.client.ui.viewmodels.intro.LoginVM
import com.hidden.client.ui.viewmodels.main.*
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(loginVM: LoginVM)

    // Dashboard
    fun inject(dashboardVM: DashboardVM)
    fun inject(dashboardTileListVM: DashboardTileListVM)
    fun inject(dashboardNumberTileViewVM: DashboardNumberTileViewVM)
    fun inject(dashboardPhotoTileViewVM: DashboardPhotoTileViewVM)

    // Candidate List
    fun inject(candidateListVM: CandidateListVM)
    fun inject(candidateViewVM: CandidateViewVM)
    fun inject(candidateDetailVM: CandidateDetailVM)

    fun inject(brandViewVM: BrandViewVM)
    fun inject(projectViewVM: ProjectViewVM)
    fun inject(workExperienceViewVM: WorkExperienceViewVM)

    // Shortlists
    fun inject(shortlistListVM: ShortlistListVM)
    fun inject(shortlistViewVM: ShortlistViewVM)
    fun inject(shortlistApproveCandidateVM: ShortlistApproveCandidateVM)
    fun inject(shortlistRejectCandidateVM: ShortlistRejectCandidateVM)
    fun inject(shortlistFeedbackVM: ShortlistFeedbackVM)
    fun inject(interviewDetailVM: InterviewDetailVM)

    // JobSetting
    fun inject(jobSettingVM: JobSettingVM)
    fun inject(reviewerViewVM: ReviewerViewVM)
    fun inject(roleAvailableUserViewVM: RoleAvailableUserViewVM)
    fun inject(jobAddUserRoleVM: JobAddUserRoleVM)
    fun inject(jobUserManagerVM: JobUserManagerVM)
    fun inject(jobInviteTeamMemberVM: JobInviteTeamMemberVM)

    // Process
    fun inject(processListVM: ProcessListVM)
    fun inject(processViewVM: ProcessViewVM)
    fun inject(processDetailVM: ProcessDetailVM)

    // Message
    fun inject(messageListVM: MessageListVM)
    fun inject(messageViewVM: MessageViewVM)
//    fun inject(messageDetailVM: MessageDetailVM)

    fun inject(feedbackVM: FeedbackVM)
    fun inject(giveFeedbackVM: GiveFeedbackVM)


    // Process Setting
    fun inject(processSettingVM: ProcessSettingVM)
    fun inject(processUserManagerVM: ProcessUserManagerVM)
    fun inject(processAddUserRoleVM: ProcessAddUserRoleVM)
    fun inject(processTabVM: ProcessTabVM)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}