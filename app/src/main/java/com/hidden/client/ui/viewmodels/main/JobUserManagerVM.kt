package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.models.dao.ReviewerDao
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.ui.adapters.ReviewerListAdapter
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class JobUserManagerVM(
    private val reviewerDao: ReviewerDao
) : RootVM() {

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val userManagerListAdapter: ReviewerListAdapter = ReviewerListAdapter()

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadUserManagerList(jobId: Int) {

        subscription = Observable.fromCallable {
            reviewerDao.getReviewerByParentIdAndReviewType(
                Enums.SettingType.JOB.value,
                Enums.ReviewerType.USER_MANAGER.value,
                jobId,
                AppPreferences.myId
            )
        }
            .concatMap { dbReviewList ->
                Observable.just(dbReviewList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveUserManagerListStart() }
            .doOnTerminate { onRetrieveUserManagerListFinish() }
            .subscribe(
                { result -> onRetrieveUserManagerListSuccess(result) },
                { error -> onRetrieveUserManagerListError(error) }
            )
    }

    private fun onRetrieveUserManagerListStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveUserManagerListFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveUserManagerListSuccess(userManagerList: List<ReviewerEntity>) {
        val userArrayLit: ArrayList<ReviewerEntity> = arrayListOf()
        userArrayLit.addAll(userManagerList)
        userManagerListAdapter.updateReviewerList(userArrayLit, 0)
    }

    private fun onRetrieveUserManagerListError(e: Throwable) {
        e.printStackTrace()
    }
}