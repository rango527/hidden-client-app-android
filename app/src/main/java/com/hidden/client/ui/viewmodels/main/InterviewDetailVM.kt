package com.hidden.client.ui.viewmodels.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.models.entity.InterviewEntity
import com.hidden.client.models.json.InterviewJson
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InterviewDetailVM (
    private val context: Context
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val interviewTitle: MutableLiveData<String> = MutableLiveData("")

    val interviewDetail: MutableLiveData<InterviewEntity> = MutableLiveData()

    private var subscription: Disposable? = null

    var processId: Int = 0
    var interviewId: Int = 0

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadInterviewDetail(processId: Int, interviewId: Int) {

        this.processId = processId
        this.interviewId = interviewId

        subscription = processApi.getInterview(
            AppPreferences.apiAccessToken,
            processId,
            interviewId
        ).concatMap { apiResult ->
            Observable.just(parseJsonResult(apiResult))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveInterviewDetailStart() }
            .doOnTerminate { onRetrieveInterviewDetailFinish() }
            .subscribe(
                { result -> onRetrieveInterviewDetailSuccess(result) },
                { error -> onRetrieveInterviewDetailError(error) }
            )

    }

    private fun parseJsonResult(json: InterviewJson): InterviewEntity {

        val interview: InterviewEntity = json.toEntity(processId)

        val interviewerList = json.toInterviewerList(processId)

        interview.setInterviewerList(interviewerList)

        Log.i("interview_detail", AppPreferences.apiAccessToken + ", " + processId + ", " + interviewId)
        Log.i("interview_detail", json.toString())
        Log.i("interview_detail", interview.toString())

        return interview
    }


    private fun onRetrieveInterviewDetailStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveInterviewDetailFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveInterviewDetailSuccess(interview: InterviewEntity) {

        interviewDetail.value = interview

        // check first name is empty or null
        interviewTitle.value = interview.description + " with " + if (interview.firstName.isNullOrEmpty()) "" else interview.firstName

        // some sub View Models

    }

    private fun onRetrieveInterviewDetailError(e: Throwable) {
        e.printStackTrace()
    }

}