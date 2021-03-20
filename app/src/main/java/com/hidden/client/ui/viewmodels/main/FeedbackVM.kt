package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.FeedbackEntity
import com.hidden.client.models.json.FeedbackJson
import com.hidden.client.models.json.SubmissionVotesJson
import com.hidden.client.models.json.TimelineJson
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import javax.inject.Inject

class FeedbackVM(
    private val context: Context
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    // To jump to Process Detail Activity after login success
    val statusAfterVote = MutableLiveData<SubmissionVotesJson>()

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val feedback = MutableLiveData<FeedbackEntity>()
    val feedbackId = MutableLiveData<Int>()

    private var subscription: Disposable? = null

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
        body: RequestBody
    ) {
        subscription = processApi.submitFeedback(
            "application/json",
            AppPreferences.apiAccessToken,
            processId,
            body
        ).concatMap { addResult ->
            Observable.just(addResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onSubmitFeedbackSuccess(result) },
                { error -> onSubmitFeedbackError(error) }
            )
    }

    fun loadTimeline(processId: Int) {
        subscription = processApi.getTimeline(AppPreferences.apiAccessToken, processId)
            .concatMap { apiResult ->
                Observable.just(getFeedbackIdFromTimeline(apiResult))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveFeedbackStart() }
            .doOnTerminate { onRetrieveFeedbackFinish() }
            .subscribe(
                { result -> onRetrieveTimelineSuccess(result) },
                { error -> onRetrieveTimelineError(error) }
            )
    }

    private fun parseJsonResult(json: FeedbackJson): FeedbackEntity {
        val feedbackEntity = json.toEntity(0)
        val questionList = json.toQuestionList(feedbackEntity.id, true)

        feedbackEntity.setQuestionList(questionList)

        return feedbackEntity
    }

    private fun getFeedbackIdFromTimeline(timelineList: List<TimelineJson>): Int {
        var feedbackId = 1

        if (timelineList.isEmpty()) return feedbackId

        for (timeline in timelineList) {
            if (timeline.feedbackIds != null) {
                if (timeline.feedbackIds.isNotEmpty()) {
                    for (feedbackIdObj in timeline.feedbackIds) {
                        if (feedbackIdObj.id != null && feedbackIdObj.clientId == AppPreferences.myId) {
                            feedbackId = feedbackIdObj.id
                            break
                        }
                    }
                }
            }
            if (feedbackId != 1) break
        }
        return feedbackId
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

    private fun onRetrieveTimelineSuccess(feedbackId: Int) {
        this.feedbackId.value = feedbackId
    }

    private fun onRetrieveFeedbackError(e: Throwable) {
        e.printStackTrace()
        HCGlobal.getInstance().log("Error  Error Error")
        val feedbackEntity = FeedbackEntity(0, 0, "", "", "", "", "", 0)
        feedbackEntity.setQuestionList(listOf())
        feedback.value = feedbackEntity
    }

    private fun onRetrieveTimelineError(e: Throwable) {
        e.printStackTrace()
    }

    private fun onSubmitFeedbackSuccess(statusAfterVoteJson: SubmissionVotesJson) {
        statusAfterVote.value = statusAfterVoteJson
    }

    private fun onSubmitFeedbackError(e: Throwable) {
        e.printStackTrace()
    }
}