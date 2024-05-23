package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.models.entity.CandidateEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class CandidateViewVM: RootVM() {

    private val candidateName = MutableLiveData<String>()
    private val candidatePhoto = MutableLiveData<String>()

    fun bind(candidate: CandidateEntity) {
        candidateName.value = candidate.fullName
        candidatePhoto.value = candidate.photo
    }

    fun getCandidateName(): MutableLiveData<String> {
        return candidateName
    }

    fun getCandidatePhoto(): MutableLiveData<String> {
        return candidatePhoto
    }
}