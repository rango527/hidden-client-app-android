package com.hidden.client.ui.viewmodels___

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models_.HCWorkExperience

class HCWorkExperienceViewModel(private var workExperience: HCWorkExperience) :
    ViewModel() {

    fun getWorkExperience(): HCWorkExperience {
        return this.workExperience
    }

    private var workExperienceListMutableLiveData = MutableLiveData<ArrayList<HCWorkExperienceViewModel>>()
    private var workExperienceList = ArrayList<HCWorkExperienceViewModel>()

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