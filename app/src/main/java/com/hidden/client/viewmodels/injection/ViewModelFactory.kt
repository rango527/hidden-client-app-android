package com.hidden.client.viewmodels.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hidden.client.viewmodels.intro.LoginVM

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}