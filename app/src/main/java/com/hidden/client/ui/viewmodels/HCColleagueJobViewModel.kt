package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models.HCJob
import com.hidden.client.DataBinderMapperImpl

class HCColleagueJobViewModel: ViewModel {

    private lateinit var job: HCJob

    constructor() : super()

    constructor(job: HCJob) : super() {
        this.job = job
    }

    fun getJob(): HCJob {
        return this.job
    }

    var colleagueJobListMutableLiveData = MutableLiveData<ArrayList<HCColleagueJobViewModel>>()
    var colleagueJobList = ArrayList<HCColleagueJobViewModel>()

    fun getColleagueJobList(): MutableLiveData<ArrayList<HCColleagueJobViewModel>> {

        val job1 = HCJob("", "UX Designer", "London")
        val job2 = HCJob("", "Tech Lead", "London")
        val job3 = HCJob("", "Social Media Manager", "Newyork, NY")
        val job4 = HCJob("", "Creative Directory", "Tokyo")

        val dashboardViewModel1: HCColleagueJobViewModel = HCColleagueJobViewModel(job1)
        val dashboardViewModel2: HCColleagueJobViewModel = HCColleagueJobViewModel(job2)
        val dashboardViewModel3: HCColleagueJobViewModel = HCColleagueJobViewModel(job3)
        val dashboardViewModel4: HCColleagueJobViewModel = HCColleagueJobViewModel(job4)

        colleagueJobList.add(dashboardViewModel1)
        colleagueJobList.add(dashboardViewModel2)
        colleagueJobList.add(dashboardViewModel3)
        colleagueJobList.add(dashboardViewModel4)

        colleagueJobListMutableLiveData.value = colleagueJobList

        return colleagueJobListMutableLiveData
    }
}