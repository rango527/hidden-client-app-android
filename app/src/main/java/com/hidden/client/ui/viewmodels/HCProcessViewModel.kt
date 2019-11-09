package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.R
import com.hidden.client.models_.HCProcess

class HCProcessViewModel: ViewModel {

    private lateinit var process: HCProcess

    constructor() : super()

    constructor(process: HCProcess) : super() {
        this.process = process
    }

    fun getProcess(): HCProcess {
        return this.process
    }

    var processListMutableLiveData = MutableLiveData<ArrayList<HCProcessViewModel>>()
    var processList = ArrayList<HCProcessViewModel>()

    fun getProcessList(): MutableLiveData<ArrayList<HCProcessViewModel>> {

        processList.add(HCProcessViewModel(
            HCProcess(
                R.drawable.man, "Tanya Walters",
                "Account Director", "New York, NY", 0, 0)
        ))

        processList.add(HCProcessViewModel(
            HCProcess(
                R.drawable.water, "Sofia Bell",
                "Software Developer", "London", 0, 0)
        ))

        processList.add(HCProcessViewModel(
            HCProcess(
                R.drawable.coca, "Andrea Robinson",
                "CTO", "San Francisco", 0, 0)
        ))

        processList.add(HCProcessViewModel(
            HCProcess(
            R.drawable.coca, "Guy Guy",
            "Recuriter", "Tokyo", 0, 0)
        ))

        processList.add(HCProcessViewModel(
            HCProcess(
                R.drawable.man, "Jim Rose",
                "DevOps Engineer", "Hamburg", 0, 0)
        ))

        processList.add(HCProcessViewModel(
            HCProcess(
                R.drawable.man, "Tanya Walters",
                "Account Director", "New York, NY", 0, 0)
        ))

        processListMutableLiveData.value = processList

        return processListMutableLiveData
    }
}