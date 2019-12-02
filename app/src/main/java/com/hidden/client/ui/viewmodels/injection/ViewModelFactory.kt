package com.hidden.client.ui.viewmodels.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.hidden.client.helpers.APP
import com.hidden.client.models.room.AppDatabase
import com.hidden.client.ui.viewmodels.main.CandidateDetailVM
import com.hidden.client.ui.viewmodels.main.CandidateListVM
import com.hidden.client.ui.viewmodels.main.ShortlistListVM

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CandidateListVM::class.java)) {
            val db = Room.databaseBuilder(
                activity.applicationContext,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return CandidateListVM(activity, db.candidateDao()) as T
        }

        if (modelClass.isAssignableFrom(CandidateDetailVM::class.java)) {
            val db = Room.databaseBuilder(
                activity.applicationContext,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return CandidateDetailVM(
                activity,
                db.candidateDao(), db.brandDao(), db.projectDao(),
                db.skillDao(), db.workExperienceDao()
            ) as T
        }

        if (modelClass.isAssignableFrom(ShortlistListVM::class.java)) {
            val db = Room.databaseBuilder(
                activity.applicationContext,
                AppDatabase::class.java,
                APP.database
            ).build()
            @Suppress("UNCHECKED_CAST")
            return ShortlistListVM(
                db.shortlistDao(),
                db.shortlistCandidateDao(),
                db.feedbackDao(),
                db.brandDao(),
                db.projectDao(),
                db.workExperienceDao(),
                db.skillDao()
            ) as T
        }

//        if (modelClass.isAssignableFrom(ShortlistListVM::class.java)) {
//            val db = Room.databaseBuilder(
//                activity.applicationContext,
//                AppDatabase::class.java,
//                APP.database
//            ).build()
//            @Suppress("UNCHECKED_CAST")
//            return ShortlistListVM(
//                db.shortlistCandidateDao()
//            ) as T
//        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}