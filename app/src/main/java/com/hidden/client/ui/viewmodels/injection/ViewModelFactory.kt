package com.hidden.client.ui.viewmodels.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.hidden.client.models.room.AppDatabase
import com.hidden.client.ui.viewmodels.intro.LoginVM
import com.hidden.client.ui.viewmodels.main.CandidateListVM

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CandidateListVM::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "candidates").build()
            @Suppress("UNCHECKED_CAST")
            return CandidateListVM(db.candidateDao()) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}