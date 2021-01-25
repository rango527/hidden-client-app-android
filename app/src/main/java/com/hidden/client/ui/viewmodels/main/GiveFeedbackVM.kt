package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.FeedbackEntity
import com.hidden.client.models.json.FeedbackJson
import com.hidden.client.models.json.SimpleResponseJson
import com.hidden.client.models.json.SubmissionVotesJson
import com.hidden.client.models.json.TimelineJson
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import javax.inject.Inject

class GiveFeedbackVM(
    private val context: Context
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    // To jump to Process Detail Activity after login success
    private val _navigateToFeedbackDone = MutableLiveData<Event<Boolean>>()
    val navigateToFeedbackDone: LiveData<Event<Boolean>>
        get() = _navigateToFeedbackDone

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val feedback = MutableLiveData<FeedbackEntity>()
    //    val feedbackId = MutableLiveData<Int>()
    var feedbackId: Int = 1
        set(value) {
            field = value
        }

    private var subscription: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadGiveFeedback(processId: Int) {

        if (feedbackId == 1) {
            feedbackId = HCGlobal.getInstance().currentFeedbackId
        }

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
        feedbackId: Int,
        body: RequestBody
    ) {
        subscription = processApi.addFeedback(
            "application/json",
            AppPreferences.apiAccessToken,
            processId,
            feedbackId,
            body
        ).concatMap { addResult ->
            Observable.just(addResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveFeedbackStart() }
            .doOnTerminate { onRetrieveFeedbackFinish() }
            .subscribe(
                { result -> onSubmitFeedbackSuccess(result) },
                { error -> onSubmitFeedbackError(error) }
            )
    }

    fun submitInterviewProposedDates(
        processId: Int,
        body: RequestBody
    ) {
        subscription = processApi.submitInterviewProposedDates("application/json", AppPreferences.apiAccessToken, processId, body)
            .concatMap { addResult -> Observable.just(addResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveFeedbackStart() }
            .doOnTerminate { onRetrieveFeedbackFinish() }
            .subscribe(
                { onSubmitInterviewProposedDatesSuccess() },
                { error -> onSubmitInterviewProposedDatesError(error) }
            )
    }

    fun nudgeFeedback(processId: Int?, feedbackId: Int?, body: RequestBody) {
        subscription = processApi.nudgeFeedback("application/json", AppPreferences.apiAccessToken, processId, feedbackId, body)
            .concatMap { addResult -> Observable.just(addResult)
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { onRetrieveFeedbackStart() }
                    .doOnTerminate { onRetrieveFeedbackFinish() }
                    .subscribe(
                        { onNudgeFeedbackSuccess() },
                        { error -> onNudgeFeedbackError(error) }
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

    private fun onSubmitFeedbackSuccess(apiResult: SimpleResponseJson) {
        _navigateToFeedbackDone.value = Event(true)
    }

    private fun onNudgeFeedbackSuccess() {
        HToast.show(HCGlobal.getInstance().currentActivity, "Nudge sent", HToast.TOAST_SUCCESS)
    }

    private fun onSubmitInterviewProposedDatesSuccess() {
        HToast.show(HCGlobal.getInstance().currentActivity, "Message sent!", HToast.TOAST_SUCCESS)
    }

    private fun onRetrieveTimelineSuccess(feedbackId: Int) {
        this.feedbackId = feedbackId
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

    private fun onSubmitFeedbackError(e: Throwable) {
        HToast.show(HCGlobal.getInstance().currentActivity, "Sorry, can't submit feedback", HToast.TOAST_ERROR)
        e.printStackTrace()
    }

    private fun onNudgeFeedbackError(e: Throwable) {
        HToast.show(HCGlobal.getInstance().currentActivity, "Sorry, can't Nudge send", HToast.TOAST_ERROR)
        e.printStackTrace()
    }

    private fun onSubmitInterviewProposedDatesError(e: Throwable) {
        HToast.show(HCGlobal.getInstance().currentActivity, "Sorry, can't Message send", HToast.TOAST_ERROR)
        e.printStackTrace()
    }
}