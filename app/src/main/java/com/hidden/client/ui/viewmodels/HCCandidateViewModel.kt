package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models.HCJob
import com.hidden.client.R
import com.hidden.client.models.HCCandidate

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

        val job1 = HCJob("", "Director", "NewYork, NY")
        val job2 = HCJob("", "Vice President", "Chicago")

        candidateList.add(HCCandidateViewModel(HCCandidate(R.drawable.test, "Tanya Walters")))
        candidateList.add(HCCandidateViewModel(HCCandidate(R.drawable.coca, "Sofia Bell")))
        candidateList.add(HCCandidateViewModel(HCCandidate(R.drawable.water, "Beatrice Carroll")))
        candidateList.add(HCCandidateViewModel(HCCandidate(R.drawable.man, "Holly Hawkins")))
        candidateList.add(HCCandidateViewModel(HCCandidate(R.drawable.test, "Minnie Daniels")))
        candidateList.add(HCCandidateViewModel(HCCandidate(R.drawable.coca, "Jason Austin")))
        candidateList.add(HCCandidateViewModel(HCCandidate(R.drawable.man, "Kirsty Crean")))
        candidateList.add(HCCandidateViewModel(HCCandidate(R.drawable.coca, "Jason Austin")))
        candidateList.add(HCCandidateViewModel(HCCandidate(R.drawable.coca, "Jason Austin")))

        candidateListMutableLiveData.value = candidateList

        return candidateListMutableLiveData
    }
}