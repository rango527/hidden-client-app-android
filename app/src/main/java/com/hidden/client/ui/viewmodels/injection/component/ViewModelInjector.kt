package com.hidden.client.ui.viewmodels.injection.component

import dagger.Component
import com.hidden.client.ui.viewmodels.injection.module.NetworkModule
import com.hidden.client.ui.viewmodels.intro.LoginVM
import com.hidden.client.ui.viewmodels.main.CandidateListVM
import com.hidden.client.ui.viewmodels.main.CandidateViewVM
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(loginVM: LoginVM)

    fun inject(candidateListVM: CandidateListVM)
    fun inject(candidateViewVM: CandidateViewVM)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}