package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models.HCJob
import com.hidden.client.R

class HCCandidatesViewModel: ViewModel {

    private var candidatePhoto: Int = R.drawable.man
    private lateinit var candidateName: String

    constructor() : super()

    constructor(candidatePhoto: Int, candidateName: String) : super() {
        this.candidatePhoto = candidatePhoto
        this.candidateName = candidateName
    }

    fun getCandidatePhoto(): Int {
        return this.candidatePhoto
    }

    fun getCandidateName(): String {
        return this.candidateName
    }

    var candidateListMutableLiveData = MutableLiveData<ArrayList<HCCandidatesViewModel>>()
    var candidateList = ArrayList<HCCandidatesViewModel>()

    fun getCandidateList(): MutableLiveData<ArrayList<HCCandidatesViewModel>> {

        val job1 = HCJob("", "Director", "NewYork, NY")
        val job2 = HCJob("", "Vice President", "Chicago")

        candidateList.add(HCCandidatesViewModel(R.drawable.man, "Tanya Walters"))
        candidateList.add(HCCandidatesViewModel(R.drawable.coca, "Sofia Bell"))
        candidateList.add(HCCandidatesViewModel(R.drawable.water, "Beatrice Carroll"))
        candidateList.add(HCCandidatesViewModel(R.drawable.man, "Holly Hawkins"))
        candidateList.add(HCCandidatesViewModel(R.drawable.man, "Minnie Daniels"))
        candidateList.add(HCCandidatesViewModel(R.drawable.coca, "Jason Austin"))
        candidateList.add(HCCandidatesViewModel(R.drawable.man, "Kirsty Crean"))

        candidateListMutableLiveData.value = candidateList

        return candidateListMutableLiveData
    }
}