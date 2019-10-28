package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.enums.JobType
import com.hidden.client.models.HCJob
import com.hidden.client.models.HCJobDetailTile

class HCJobDetailTileViewModel: ViewModel {

    private lateinit var jobDetailTile: HCJobDetailTile

    constructor() : super()

    constructor(jobDetailTile: HCJobDetailTile) : super() {
        this.jobDetailTile = jobDetailTile
    }

    fun getJobDetailTile(): HCJobDetailTile {
        return this.jobDetailTile
    }

    var jobDetailTileListMutableLiveData = MutableLiveData<ArrayList<HCJobDetailTileViewModel>>()
    var jobDetailTileList = ArrayList<HCJobDetailTileViewModel>()

    fun getJobDetailTileList(): MutableLiveData<ArrayList<HCJobDetailTileViewModel>> {
       return jobDetailTileListMutableLiveData
    }

    fun setJobDetailTileList(jobDetailTileList: List<HCJobDetailTile>) {

        var tempJobList: ArrayList<HCJobDetailTileViewModel> = arrayListOf();

        for (job in jobDetailTileList) {
            tempJobList.add(HCJobDetailTileViewModel(job))
        }

        this.jobDetailTileList.clear()
        this.jobDetailTileList.addAll(tempJobList)

        this.jobDetailTileListMutableLiveData.value = this.jobDetailTileList

    }

}