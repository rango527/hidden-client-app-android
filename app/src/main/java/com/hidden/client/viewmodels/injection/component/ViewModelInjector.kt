package com.hidden.client.viewmodels.injection.component

import dagger.Component
import com.hidden.client.viewmodels.injection.module.NetworkModule
import com.hidden.client.viewmodels.intro.LoginVM
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(LoginViewModel: LoginVM)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}