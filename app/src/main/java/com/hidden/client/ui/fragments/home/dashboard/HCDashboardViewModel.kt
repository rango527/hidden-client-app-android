package com.hidden.client.ui.fragments.home.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models.HCJob

class HCDashboardViewModel : ViewModel {

    private lateinit var job: HCJob

    constructor() : super()

    constructor(job: HCJob) : super() {
        this.job = job
    }

    var yourJobListMutableLiveData = MutableLiveData<ArrayList<HCDashboardViewModel>>()
    var yourJobList = ArrayList<HCDashboardViewModel>()

    fun getYourJobList(): MutableLiveData<ArrayList<HCDashboardViewModel>> {

        val job1 = HCJob("", "Director", "NewYork, NY")
        val job2 = HCJob("", "Vice President", "Chicago")

        val dashboardViewModel1: HCDashboardViewModel = HCDashboardViewModel(job1)
        val dashboardViewModel2: HCDashboardViewModel = HCDashboardViewModel(job2)

        yourJobList.add(dashboardViewModel1)
        yourJobList.add(dashboardViewModel2)

        yourJobListMutableLiveData.value = yourJobList

        return yourJobListMutableLiveData
    }
}