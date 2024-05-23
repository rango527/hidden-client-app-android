package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.CandidateApi
import com.hidden.client.models.json.CandidateJson
import com.hidden.client.models.dao.*
import com.hidden.client.models.entity.CandidateEntity
import com.hidden.client.ui.adapters.CandidateListAdapter
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CandidateDetailVM(private val context: Context,
                        private val candidateDao: CandidateDao,
                        private val brandDao: BrandDao,
                        private val projectDao: ProjectDao,
                        private val skillDao: SkillDao,
                        private val workExperienceDao: WorkExperienceDao): RootVM() {

    var candidateId = 0
        set(value) {
            field = value
        }

    @Inject
    lateinit var candidateApi: CandidateApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var subscription: Disposable

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}