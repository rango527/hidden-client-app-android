package com.hidden.client.ui.viewmodels___

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models_.HCWorkExperience

class HCWorkExperienceViewModel: ViewModel {

    private lateinit var workExperience: HCWorkExperience

    constructor() : super()

    constructor(workExperience: HCWorkExperience) : super() {
        this.workExperience = workExperience
    }

    fun getWorkExperience(): HCWorkExperience {
        return this.workExperience
    }

    var workExperienceListMutableLiveData = MutableLiveData<ArrayList<HCWorkExperienceViewModel>>()
    var workExperienceList = ArrayList<HCWorkExperienceViewModel>()

    fun getWorkExperienceList(): MutableLiveData<ArrayList<HCWorkExperienceViewModel>> {

        return workExperienceListMutableLiveData
    }

    fun setWorkExperienceList(workExperienceList: List<HCWorkExperience>) {

        this.workExperienceList.clear()

        for (workExperience in workExperienceList) {
            this.workExperienceList.add(HCWorkExperienceViewModel(workExperience))
        }

        workExperienceListMutableLiveData.value = this.workExperienceList
    }
}