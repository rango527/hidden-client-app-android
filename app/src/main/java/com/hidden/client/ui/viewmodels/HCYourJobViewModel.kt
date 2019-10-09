package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models.HCJob

class HCYourJobViewModel: ViewModel {

    private lateinit var job: HCJob

    constructor() : super()

    constructor(job: HCJob) : super() {
        this.job = job
    }

    var yourJobListMutableLiveData = MutableLiveData<ArrayList<HCYourJobViewModel>>()
    var yourJobList = ArrayList<HCYourJobViewModel>()

    fun getYourJobList(): MutableLiveData<ArrayList<HCYourJobViewModel>> {

        val job1 = HCJob("", "Director", "NewYork, NY")
        val job2 = HCJob("", "Vice President", "Chicago")

        val dashboardViewModel1: HCYourJobViewModel = HCYourJobViewModel(job1)
        val dashboardViewModel2: HCYourJobViewModel = HCYourJobViewModel(job2)

        yourJobList.add(dashboardViewModel1)
        yourJobList.add(dashboardViewModel2)

        yourJobListMutableLiveData.value = yourJobList

        return yourJobListMutableLiveData
    }
}