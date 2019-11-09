package com.hidden.client.viewmodels.root

import androidx.lifecycle.ViewModel
import com.hidden.client.viewmodels.injection.component.DaggerViewModelInjector
import com.hidden.client.viewmodels.injection.component.ViewModelInjector

import com.hidden.client.viewmodels.injection.module.NetworkModule
import com.hidden.client.viewmodels.intro.LoginVM

abstract class RootVM: ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is LoginVM -> injector.inject(this)
        }
    }
}