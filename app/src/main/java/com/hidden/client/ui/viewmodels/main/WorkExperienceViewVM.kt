package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.models.BrandEntity
import com.hidden.client.models.WorkExperienceEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class WorkExperienceViewVM: RootVM() {

    private val experience = MutableLiveData<WorkExperienceEntity>()

    fun bind(experience: WorkExperienceEntity) {
        this.experience.value = experience
    }

    fun getExperience(): MutableLiveData<WorkExperienceEntity> {
        return experience
    }
}