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

    // Shortlists
    fun inject(shortlistListVM: ShortlistListVM)
    fun inject(sthortlistViewVM: ShortlistViewVM)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}