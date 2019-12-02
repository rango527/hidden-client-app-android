package com.hidden.client.ui.viewmodels.injection.component

import dagger.Component
import com.hidden.client.ui.viewmodels.injection.module.NetworkModule
import com.hidden.client.ui.viewmodels.intro.LoginVM
import com.hidden.client.ui.viewmodels.main.*
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(loginVM: LoginVM)

    fun inject(candidateListVM: CandidateListVM)
    fun inject(candidateViewVM: CandidateViewVM)
    fun inject(candidateDetailVM: CandidateDetailVM)
    fun inject(shortlistListVM: ShortlistListVM)
    fun inject(shortlistViewVM: ShortlistViewVM)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}