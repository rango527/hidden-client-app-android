package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.models.entity.FeedbackEntity
import com.hidden.client.models.json.FeedbackJson
import com.hidden.client.models.json.SimpleResponseJson
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

class FeedbackVM (
    private val context: Context
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    // To jump to Process Detail Activity after login success
    private val _navigateToShortlist = MutableLiveData<Event<Boolean>>()
    val navigateToShortlist: LiveData<Event<Boolean>>
        get() = _navigateToShortlist

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val feedback = MutableLiveData<FeedbackEntity>()

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadFeedback(processId: Int, feedbackId: Int) {

        subscription = processApi.getFeedback(
            AppPreferences.apiAccessToken,
            processId,
            feedbackId
        ).concatMap { apiResult ->
            Observable.just(parseJsonResult(apiResult))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveFeedbackStart() }
            .doOnTerminate { onRetrieveFeedbackFinish() }
            .subscribe(
                { result -> onRetrieveFeedbackSuccess(result) },
                { error -> onRetrieveFeedbackError(error) }
            )
    }

    fun submitFeedback(
        processId: Int,
        vote: String,
        answers: JSONObject,
        comment: String
    ) {
        subscription = processApi.submitFeedback(
            AppPreferences.apiAccessToken,
            processId,
            vote,
            answers,
            comment
        ).concatMap { addResult ->
            Observable.just(addResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveFeedbackStart() }
            .doOnTerminate { onRetrieveFeedbackFinish() }
            .subscribe(
                { result -> onSubmitFeedbackSuccess(result) },
                { error -> onRetrieveFeedbackError(error) }
            )
    }

    private fun parseJsonResult(json: FeedbackJson): FeedbackEntity {
        val feedbackEntity = json.toEntity(0)
        val questionList = json.toQuestionList(feedbackEntity.id)

        feedbackEntity.setQuestionList(questionList)

        return feedbackEntity
    }

    private fun onRetrieveFeedbackStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveFeedbackFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveFeedbackSuccess(feedbackEntity: FeedbackEntity) {
        feedback.value = feedbackEntity
    }

    fun onSubmitFeedbackSuccess(result: SimpleResponseJson) {
        _navigateToShortlist.value = Event(true)
    }

    private fun onRetrieveFeedbackError(e: Throwable) {
        e.printStackTrace()
    }
}