package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ShortlistApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.models.dao.ShortlistCandidateDao
import com.hidden.client.models.entity.ShortlistEntity
import com.hidden.client.models.json.SimpleResponseJson
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShortlistApproveCandidateVM(
    private val shortlistCandidateDao: ShortlistCandidateDao
) : RootVM() {

    @Inject
    lateinit var shortlistApi: ShortlistApi

//    private var processId: Int = 0
//        set(value) {
//            field = value
//        }

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val shortlist: MutableLiveData<ShortlistEntity> = MutableLiveData()

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun approveCandidate(processId: Int) {
        subscription = Observable.fromCallable { }
            .concatMap {
                shortlistApi.approveCandidate(AppPreferences.apiAccessToken, processId)
                    .concatMap { apiApprove ->
                        Observable.just(apiApprove)
                    }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onApproveStart() }
            .doOnTerminate { onApproveFinish() }
            .subscribe(
                { result -> onApproveSuccess(result) },
                { error -> onApproveError(error) }
            )
    }

    private fun onApproveStart() {
        loadingVisibility.value = true
    }

    private fun onApproveFinish() {
        loadingVisibility.value = false
    }

    private fun onApproveSuccess(response: SimpleResponseJson) {
//        this.shortlist.value = shortlist
//
//        val shortlistViewVMList: ArrayList<ShortlistViewVM> = arrayListOf()
//        for (candidate in shortlist.getCandidateList()) {
//            shortlistViewVMList.add(ShortlistViewVM(candidate))
//        }
//        this.candidateList.value = shortlistViewVMList
    }

    private fun onApproveError(e: Throwable) {
        e.printStackTrace()
    }
}