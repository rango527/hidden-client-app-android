package com.hidden.client.ui.fragments.home.shortlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShortlistsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is shortlists Fragment"
    }
    val text: LiveData<String> = _text
}