package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.models.custom.UserManager
import com.hidden.client.ui.viewmodels.root.RootVM

class UserManagerViewVM: RootVM() {

    private val reviewerName = MutableLiveData<String>()
    private val reviewerPhoto = MutableLiveData<String>()
    private val tick = MutableLiveData<Boolean>()

    fun bind(reviewer: UserManager) {
        reviewerName.value = reviewer.user.fullName
        reviewerPhoto.value = reviewer.user.avatar
        tick.value = reviewer.tick
    }

    fun getReviewerName(): MutableLiveData<String> {
        return reviewerName
    }

    fun getReviewerPhoto(): MutableLiveData<String> {
        return reviewerPhoto
    }

    fun getTick(): MutableLiveData<Boolean> {
        return tick
    }
}