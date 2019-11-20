package com.hidden.client.ui.viewmodels.main

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.CandidateApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.Candidate
import com.hidden.client.models.dao.*
import com.hidden.client.ui.adapters.CandidateListAdapter
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CandidateDetailVM(private val context: Context,
                        private val candidateDao: CandidateDao,
                        private val candidateBrandDao: CandidateBrandDao,
                        private val candidateProjectDao: CandidateProjectDao,
                        private val candidateSkillDao: CandidateSkillDao,
                        private val candidateWorkExperienceDao: CandidateWorkExperienceDao): RootVM() {

    var candidateId = 0
        set(value) {
            field = value
        }

    @Inject
    lateinit var candidateApi: CandidateApi
    val candidateListAdapter: CandidateListAdapter = CandidateListAdapter()

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()

    val candidateInfo: MutableLiveData<Candidate> = MutableLiveData()

    private lateinit var subscription: Disposable

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadCandidateList(getOnlyFromLocal: Boolean){
        subscription = Observable.fromCallable { candidateDao.getCandidateById(candidateId) }
            .concatMap {
                    dbCandidateList ->
                if(dbCandidateList.isEmpty() && !getOnlyFromLocal)
                    candidateApi.getCandidateById(AppPreferences.apiAccessToken, candidateId.toString()).concatMap {
                        apiCandidateList -> candidateDao.insertAll(*apiCandidateList.toTypedArray())
                        Observable.just(apiCandidateList)
                    }
                else
                    Observable.just(dbCandidateList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCandidateListStart() }
            .doOnTerminate { onRetrieveCandidateListFinish() }
            .subscribe(
                { result -> onRetrieveCandidateListSuccess(result) },
                { error -> onRetrieveCandidateListError(error) }
            )
    }

    private fun onRetrieveCandidateListStart(){
        loadingVisibility.value = true
        errorMessage.value = null
    }

    private fun onRetrieveCandidateListFinish(){
        loadingVisibility.value = false
    }

    private fun onRetrieveCandidateListSuccess(candidateList: List<Candidate>){
        candidateListAdapter.updateCandidateList(candidateList)
    }

    private fun onRetrieveCandidateListError(e: Throwable){
        e.printStackTrace()
        errorMessage.value = R.string.server_error
    }
}