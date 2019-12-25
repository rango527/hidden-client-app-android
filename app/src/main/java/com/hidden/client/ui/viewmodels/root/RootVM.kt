package com.hidden.client.ui.viewmodels.root

import androidx.lifecycle.ViewModel
import com.hidden.client.ui.viewmodels.injection.component.DaggerViewModelInjector
import com.hidden.client.ui.viewmodels.injection.component.ViewModelInjector

import com.hidden.client.ui.viewmodels.injection.module.NetworkModule
import com.hidden.client.ui.viewmodels.intro.LoginVM
import com.hidden.client.ui.viewmodels.main.*

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

            // Dashboard
            is DashboardVM -> injector.inject(this)

            // CandidateList
            is CandidateListVM -> injector.inject(this)
            is CandidateViewVM -> injector.inject(this)

            // Shortlists
            is ShortlistListVM -> injector.inject(this)
        }
    }
}