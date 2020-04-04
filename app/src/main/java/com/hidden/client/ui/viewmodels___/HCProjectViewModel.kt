package com.hidden.client.ui.viewmodels___

import androidx.lifecycle.ViewModel
import com.hidden.client.models_.HCProject

class HCProjectViewModel(private var project: HCProject) : ViewModel() {

    fun getProject(): HCProject {
        return this.project
    }

    var projectList = ArrayList<HCProjectViewModel>()
}