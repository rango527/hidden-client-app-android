package com.hidden.client.ui.viewmodels___

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models_.HCBrand
import com.hidden.client.models_.HCProject

class HCProjectViewModel: ViewModel {

    private lateinit var project: HCProject

    constructor() : super()

    constructor(project: HCProject) : super() {
        this.project = project
    }

    fun getProject(): HCProject {
        return this.project
    }

    var projectListMutableLiveData = MutableLiveData<ArrayList<HCProjectViewModel>>()
    var projectList = ArrayList<HCProjectViewModel>()

    fun getProjectList(): MutableLiveData<ArrayList<HCProjectViewModel>> {

        return projectListMutableLiveData
    }

    fun setProjectList(projectList: List<HCProject>) {

        this.projectList.clear()

        for (project in projectList) {
            this.projectList.add(HCProjectViewModel(project))
        }

        projectListMutableLiveData.value = this.projectList
    }
}