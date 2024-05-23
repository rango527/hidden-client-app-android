package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class ReviewerViewVM: RootVM() {

    private val reviewerName = MutableLiveData<String>()
    private val reviewerPhoto = MutableLiveData<String>()

    fun bind(reviewer: ReviewerEntity) {
        reviewerName.value = reviewer.fullName
        reviewerPhoto.value = reviewer.avatar
    }

    fun getReviewerName(): MutableLiveData<String> {
        return reviewerName
    }

    fun getReviewerPhoto(): MutableLiveData<String> {
        return reviewerPhoto
    }
}