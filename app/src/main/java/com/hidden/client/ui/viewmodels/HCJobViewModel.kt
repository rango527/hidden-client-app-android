package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models.HCJob

class HCJobViewModel: ViewModel {

    private lateinit var job: HCJob

    constructor() : super()

    constructor(job: HCJob) : super() {
        this.job = job
    }

    fun getJob(): HCJob {
        return this.job
    }

    var jobListMutableLiveData = MutableLiveData<ArrayList<HCJobViewModel>>()
    var jobList = ArrayList<HCJobViewModel>()

    fun getJobList(): MutableLiveData<ArrayList<HCJobViewModel>> {
        return jobListMutableLiveData
    }

    fun setJobList(jobList: List<HCJob>) {

        this.jobList.clear()

        for (job in jobList) {
            this.jobList.add(HCJobViewModel(job))
        }

        jobListMutableLiveData.value = this.jobList
    }
}