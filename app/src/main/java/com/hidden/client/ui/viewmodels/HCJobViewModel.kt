package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.enums.JobType
import com.hidden.client.models_.HCJob

class HCJobViewModel: ViewModel {

    private lateinit var job: HCJob

    constructor() : super()

    constructor(job: HCJob) : super() {
        this.job = job
    }

    fun getJob(): HCJob {
        return this.job
    }

    var yourJobListMutableLiveData = MutableLiveData<ArrayList<HCJobViewModel>>()
    var yourJobList = ArrayList<HCJobViewModel>()

    var colleagueJobListMutableLiveData = MutableLiveData<ArrayList<HCJobViewModel>>()
    var colleagueJobList = ArrayList<HCJobViewModel>()

    fun getJobList(jobType: String): MutableLiveData<ArrayList<HCJobViewModel>> {
        return when (jobType) {
            JobType.YOUR_JOB.value -> {
                yourJobListMutableLiveData
            }
            JobType.COLLEAGUE_JOB.value-> {
                colleagueJobListMutableLiveData
            }
            else -> {
                yourJobListMutableLiveData
            }
        }
    }

    fun setJobList(jobList: List<HCJob>, jobType: String) {

        var tempJobList: ArrayList<HCJobViewModel> = arrayListOf();

        for (job in jobList) {
            tempJobList.add(HCJobViewModel(job))
        }

        if (jobType == JobType.YOUR_JOB.value) {

            this.yourJobList.clear()
            this.yourJobList.addAll(tempJobList)

            this.yourJobListMutableLiveData.value = this.yourJobList

        } else if (jobType == JobType.COLLEAGUE_JOB.value) {

            this.colleagueJobList.clear()
            this.colleagueJobList.addAll(tempJobList)

            this.colleagueJobListMutableLiveData.value = this.colleagueJobList
        }
    }

}