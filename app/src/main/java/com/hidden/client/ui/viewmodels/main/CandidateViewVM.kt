package com.hidden.client.ui.viewmodels.main

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.CandidateEntity
import com.hidden.client.ui.viewmodels.root.RootVM
import com.rishabhharit.roundedimageview.RoundedImageView

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

    companion object {

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: RoundedImageView, photoUrl: String?) {
            if (photoUrl !== null) {
                Glide.with(HCGlobal.getInstance().currentActivity).load(photoUrl).into(imageView)
            }
        }
    }
}