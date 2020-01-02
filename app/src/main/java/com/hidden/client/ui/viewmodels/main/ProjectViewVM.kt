package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.models.ProjectEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class ProjectViewVM: RootVM() {

    private val project = MutableLiveData<ProjectEntity>()

    fun bind(project: ProjectEntity) {
        this.project.value = project
    }

    fun getMainImage(): String {
        if (project.value!!.getAssetsList().isEmpty()) return ""

        for (assets in project.value!!.getAssetsList()) {
            if (assets.mainImage) {
                return assets.url
            }
        }

        return ""
    }
}