package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models_.HCCandidate

class HCCandidateViewModel: ViewModel {

    private lateinit var candidate: HCCandidate

    constructor() : super()

    constructor(candidate: HCCandidate) : super() {
        this.candidate = candidate
    }

    fun getCandidate(): HCCandidate {
        return this.candidate
    }

    var candidateListMutableLiveData = MutableLiveData<ArrayList<HCCandidateViewModel>>()
    var candidateList = ArrayList<HCCandidateViewModel>()

    fun getCandidateList(): MutableLiveData<ArrayList<HCCandidateViewModel>> {

        return candidateListMutableLiveData
    }

    fun setCandidateList(candidateList: List<HCCandidate>) {

        this.candidateList.clear()

        for (candidate in candidateList) {
            this.candidateList.add(HCCandidateViewModel(candidate))
        }

        candidateListMutableLiveData.value = this.candidateList
    }
}