package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.CandidateApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.models.dao.CandidateDao
import com.hidden.client.models.entity.CandidateEntity
import com.hidden.client.ui.adapters.CandidateListAdapter
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CandidateListVM(private val candidateDao: CandidateDao): RootVM() {

    @Inject
    lateinit var candidateApi: CandidateApi
    val candidateListAdapter: CandidateListAdapter = CandidateListAdapter()

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()

    var search = ""
        set(value) {
            field = value
            loadCandidateList(true)
        }

    private lateinit var subscription: Disposable

    init {
        loadCandidateList(false)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun loadCandidateList(getOnlyFromLocal: Boolean){
        subscription = Observable.fromCallable { candidateDao.getCandidates("%$search%") }
            .concatMap {
                    dbCandidateList ->
                if(dbCandidateList.isEmpty() && !getOnlyFromLocal)
                    candidateApi.getCandidateList(AppPreferences.apiAccessToken, search).concatMap {
                        apiCandidateList ->
                            val candidateList: ArrayList<CandidateEntity> = arrayListOf()
                            for (item in apiCandidateList) {
                                candidateList.add(item.toEntity())
                            }
                            candidateDao.insertAll(*candidateList.toTypedArray())
                            Observable.just(candidateList)
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

    private fun onRetrieveCandidateListSuccess(candidateList: List<CandidateEntity>){
        candidateListAdapter.updateCandidateList(candidateList)
    }

    private fun onRetrieveCandidateListError(e: Throwable){
        e.printStackTrace()
        errorMessage.value = R.string.server_error
    }
}