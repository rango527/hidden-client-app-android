package com.hidden.client.ui.viewmodels.root

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.ui.viewmodels.custom.DashboardNumberTileViewVM
import com.hidden.client.ui.viewmodels.custom.DashboardPhotoTileViewVM
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

            is ShortlistApproveCandidateVM -> injector.inject(this)
            is ShortlistRejectCandidateVM -> injector.inject(this)

            // JobSetting
            is JobSettingVM -> injector.inject(this)
            is ReviewerViewVM -> injector.inject(this)
            is RoleAvailableUserViewVM -> injector.inject(this)
            is JobAddUserRoleVM -> injector.inject(this)
            is JobUserManagerVM -> injector.inject(this)
            is JobInviteTeamMemberVM -> injector.inject(this)

            // Process
            is ProcessListVM -> injector.inject(this)
            is ProcessViewVM -> injector.inject(this)
            is ProcessDetailVM -> injector.inject(this)

            // ProcessSetting
            is ProcessSettingVM -> injector.inject(this)
            is ProcessUserManagerVM -> injector.inject(this)
            is ProcessAddUserRoleVM -> injector.inject(this)
        }
    }

    // Binding Adapter
    companion object {

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: ImageView, photoUrl: String) {
            if (photoUrl.safeValue() != "") {
                Glide.with(HCGlobal.getInstance().currentActivity).load(photoUrl.safeValue()).into(imageView)
            }
        }
    }
}