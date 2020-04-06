package com.hidden.client.ui.viewmodels.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.models.entity.*
import com.hidden.client.models.json.ShortlistFeedbackJson
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShortlistFeedbackVM(
    private val context: Context
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val shortlistFeedback: MutableLiveData<ShortlistFeedbackEntity> = MutableLiveData()

    val shortlistReviewerViewVMList: MutableLiveData<List<ShortlistReviewerViewVM>> = MutableLiveData()

    private var subscription: Disposable? = null

    var processId: Int = 0

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadShortlistFeedback(processId: Int) {

        this.processId = processId

        subscription = processApi.getShortlistFeedback(
            AppPreferences.apiAccessToken,
            processId
        ).concatMap { apiResult ->
            Observable.just(parseJsonResult(apiResult))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveShortlistFeedbackStart() }
            .doOnTerminate { onRetrieveShortlistFeedbackFinish() }
            .subscribe(
                { result -> onRetrieveShortlistFeedbackSuccess(result) },
                { error -> onRetrieveShortlistFeedbackError(error) }
            )

    }

    private fun parseJsonResult(json: ShortlistFeedbackJson): ShortlistFeedbackEntity {

        val shortlistFeedback: ShortlistFeedbackEntity = json.toEntity(processId)

        val shortlistReviewerList = json.toShortlistReviewerList(processId)

        shortlistFeedback.setShortlistReviewerList(shortlistReviewerList)

        return shortlistFeedback
    }


    private fun onRetrieveShortlistFeedbackStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveShortlistFeedbackFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveShortlistFeedbackSuccess(shortlistFeedbackEntity: ShortlistFeedbackEntity) {

        shortlistFeedback.value = shortlistFeedbackEntity

        val shortlistReviewerViewVMList: ArrayList<ShortlistReviewerViewVM> = arrayListOf()
        for (shortlistFeedback in shortlistFeedbackEntity.getShortlistReviewerList()) {
            shortlistReviewerViewVMList.add(ShortlistReviewerViewVM(context, shortlistFeedback))
        }
        this.shortlistReviewerViewVMList.value = shortlistReviewerViewVMList

    }

    private fun onRetrieveShortlistFeedbackError(e: Throwable) {
        e.printStackTrace()
    }
}