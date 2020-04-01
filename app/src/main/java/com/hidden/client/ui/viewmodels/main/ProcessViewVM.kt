package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.models.entity.CandidateEntity
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class ProcessViewVM: RootVM() {

    private val candidateName = MutableLiveData<String>()
    private val candidateAvatar = MutableLiveData<String>()
    private val jobTitle = MutableLiveData<String>()
    private val candidateCityName = MutableLiveData<String>()

    fun bind(process: ProcessEntity) {
        candidateName.value = process.candidateFullName
        candidateAvatar.value = process.candidateAvatar
        jobTitle.value = process.jobTitle
        candidateCityName.value = process.candidateCityName
    }

    fun getCandidateName(): MutableLiveData<String> {
        return candidateName
    }

    fun getCandidateAvatar(): MutableLiveData<String> {
        return candidateAvatar
    }

    fun getJobTitle(): MutableLiveData<String> {
        return jobTitle
    }

    fun getCandidateCityName(): MutableLiveData<String> {
        return candidateCityName
    }
}