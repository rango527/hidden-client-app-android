package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.helpers.HCDate
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.WorkExperienceEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class WorkExperienceViewVM: RootVM() {

    private val experience = MutableLiveData<WorkExperienceEntity>()

    fun bind(experience: WorkExperienceEntity) {
        this.experience.value = experience
    }

    fun getExperience(): MutableLiveData<WorkExperienceEntity> {
        return experience
    }

    fun getWorkingPeriod(): String {

        val experienceFrom = experience.value!!.workedFrom
        val experienceTo = experience.value!!.workTo

        if (experienceFrom.safeValue() == "")
            return ""

        var strDate: String

        val fromDate = HCDate.stringToDate(experienceFrom!!, null)

        strDate = HCDate.dateToString(fromDate!!, "d MMMM yyyy").toString()

        if (experienceTo.safeValue() == "" ) {

            return strDate + " - " + HCGlobal.getInstance().currentActivity.resources.getString(R.string.present)

        }

        val toDate = HCDate.stringToDate(experienceTo!!, null)

        return strDate + " - " + HCDate.dateToString(toDate!!, "d MMMM yyyy").toString()
    }
}