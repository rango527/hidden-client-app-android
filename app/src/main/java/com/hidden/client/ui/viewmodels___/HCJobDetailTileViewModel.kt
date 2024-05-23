package com.hidden.client.ui.viewmodels___

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models_.HCJobDetailTile

class HCJobDetailTileViewModel(private var jobDetailTile: HCJobDetailTile) : ViewModel() {

    fun getJobDetailTile(): HCJobDetailTile {
        return this.jobDetailTile
    }

    private var jobDetailTileListMutableLiveData = MutableLiveData<ArrayList<HCJobDetailTileViewModel>>()
    private var jobDetailTileList = ArrayList<HCJobDetailTileViewModel>()

    fun getJobDetailTileList(): MutableLiveData<ArrayList<HCJobDetailTileViewModel>> {
       return jobDetailTileListMutableLiveData
    }

    fun setJobDetailTileList(jobDetailTileList: List<HCJobDetailTile>) {

        val tempJobList: ArrayList<HCJobDetailTileViewModel> = arrayListOf();

        for (job in jobDetailTileList) {
            tempJobList.add(HCJobDetailTileViewModel(job))
        }

        this.jobDetailTileList.clear()
        this.jobDetailTileList.addAll(tempJobList)

        this.jobDetailTileListMutableLiveData.value = this.jobDetailTileList

    }

}