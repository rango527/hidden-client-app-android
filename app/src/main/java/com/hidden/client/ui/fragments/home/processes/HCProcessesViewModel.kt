package com.hidden.client.ui.fragments.home.processes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HCProcessesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is processes Fragment"
    }
    val text: LiveData<String> = _text
}