package com.hidden.client.ui.viewmodels.root

import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.viewmodels.custom.DashboardNumberTileViewVM
import com.hidden.client.ui.viewmodels.custom.DashboardPhotoTileViewVM
import com.hidden.client.ui.viewmodels.injection.component.DaggerViewModelInjector
import com.hidden.client.ui.viewmodels.injection.component.ViewModelInjector
import com.hidden.client.ui.viewmodels.injection.module.NetworkModule
import com.hidden.client.ui.viewmodels.intro.LoginVM
import com.hidden.client.ui.viewmodels.main.*
import de.hdodenhof.circleimageview.CircleImageView

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
            is DashboardTileListVM -> injector.inject(this)
            is DashboardNumberTileViewVM -> injector.inject(this)
            is DashboardPhotoTileViewVM -> injector.inject(this)

            // CandidateList
            is CandidateListVM -> injector.inject(this)
            is CandidateViewVM -> injector.inject(this)

            is BrandViewVM -> injector.inject(this)
            is ProjectViewVM -> injector.inject(this)
            is WorkExperienceViewVM -> injector.inject(this)

            // Shortlists
            is ShortlistListVM -> injector.inject(this)
            is ShortlistViewVM -> injector.inject(this)
        }
    }

    // Binding Adapter
    companion object {

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: CircleImageView, photoUrl: String) {
            Glide.with(HCGlobal.getInstance().currentActivity).load(photoUrl).into(imageView)
        }
    }
}