package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.dao.*
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.models.entity.ProcessStageEntity
import com.hidden.client.models.entity.TimelineEntity
import com.hidden.client.models.json.ProcessJson
import com.hidden.client.models.json.TimelineJson
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProcessDetailVM(
    private val context: Context,
    private val processDao: ProcessDao,
    private val processStageDao: ProcessStageDao,
    private val timelineDao: TimelineDao,
    private val interviewParticipantDao: InterviewParticipantDao,
    private val feedbackIDDao: FeedbackIDDao
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    var process: MutableLiveData<ProcessEntity> = MutableLiveData()
    var loadProcess: MutableLiveData<Boolean> = MutableLiveData(false)

    val candidateAvatar: MutableLiveData<String> = MutableLiveData("")
    val isInterviewAdvancer: MutableLiveData<Boolean> = MutableLiveData(false)
    val candidateFullName: MutableLiveData<String> = MutableLiveData("")
    val jobTitle: MutableLiveData<String> = MutableLiveData("")

    val timelineList: MutableLiveData<List<TimelineEntity>> = MutableLiveData()

    private var subscription: Disposable? = null

    var processId: Int = 0

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadProcess() {
        val apiObservable: Observable<List<ProcessEntity>>
        apiObservable = Observable.fromCallable { }
            .concatMap {
                processApi.getProcessList(AppPreferences.apiAccessToken)
                    .concatMap { apiProcess ->
                        Observable.just(parseJsonProcessListResult(apiProcess))
                    }
            }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveProcessSuccess(result) },
                { error -> onRetrieveProcessError(error) }
            )
    }

    fun loadProcessDetail() {
        subscription = processApi.getProcessDetail(AppPreferences.apiAccessToken, processId)
            .concatMap { getResult ->
                val process = getResult.toEntity(processId)
                val processStageList: List<ProcessStageEntity> = processStageDao.getStageByProcess(processId)
                process.setStageList(processStageList)
                Observable.just(process)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveProcessDetailStart() }
            .doOnTerminate { onRetrieveProcessDetailFinish() }
            .subscribe(
                { result -> onRetrieveProcessDetailSuccess(result) },
                { error -> onRetrieveProcessDetailError(error) }
            )
    }

    fun loadTimeline(cashMode: Boolean) {
        val apiObservable: Observable<List<TimelineEntity>>

        HCGlobal.getInstance().log(processId.toString())

        if (cashMode) {
            apiObservable =
                Observable.fromCallable { timelineDao.getTimeline(processId) }
                    .concatMap { dbTimelineData ->
                        if (dbTimelineData.isEmpty()) {
                            processApi.getTimeline(AppPreferences.apiAccessToken, processId)
                                .concatMap { apiTimelineList ->
                                    Observable.just(parseJsonResult(apiTimelineList))
                                }
                        } else {
                            Observable.just(parseEntityResult(dbTimelineData))
                        }
                    }
        } else {
            apiObservable = Observable.fromCallable { }
                .concatMap {
                    processApi.getTimeline(AppPreferences.apiAccessToken, processId)
                        .concatMap { apiTimelineList ->
                            Observable.just(parseJsonResult(apiTimelineList))
                        }
                }
        }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveProcessDetailStart() }
            .doOnTerminate { onRetrieveProcessDetailFinish() }
            .subscribe(
                { result -> onRetrieveTimelineSuccess(result) },
                { error -> onRetrieveProcessDetailError(error) }
            )
    }

    private fun parseJsonResult(json: List<TimelineJson>): List<TimelineEntity> {

        timelineDao.deleteByProcessId(processId)

        interviewParticipantDao.deleteByProcessId(processId)
        feedbackIDDao.deleteByProcessId(processId)

        val timelineList: ArrayList<TimelineEntity> = arrayListOf()
        for (timeline in json) {
            val timelineEntity: TimelineEntity = timeline.toEntity(processId)
            val id: Int = timelineDao.insert(timelineEntity).toInt()

            if (id > 0) {
                timelineEntity.id = id

                val interviewParticipantList = timeline.toInterviewParticipantEntityList(id, processId)
                val feedbackIdList = timeline.toFeedbackIDEntityList(id, processId)

                interviewParticipantDao.insertAll(*interviewParticipantList.toTypedArray())
                feedbackIDDao.insertAll(*feedbackIdList.toTypedArray())

                timelineEntity.setInterviewParticipantList(interviewParticipantList)
                timelineEntity.setFeedbackIdList(feedbackIdList)

                timelineList.add(timelineEntity)
            }
        }

        return timelineList
    }

    private fun parseJsonProcessListResult(jsonList: List<ProcessJson>): List<ProcessEntity> {
        processDao.deleteAll()
        processStageDao.deleteAll()

        val processList: ArrayList<ProcessEntity> = arrayListOf()

        for (json in jsonList) {
            val process: ProcessEntity = json.toEntity(AppPreferences.myId)
            val processStageList = json.toStageEntityList(process.id)

            process.setStageList(processStageList)
            processList.add(process)
            processStageDao.insertAll(*processStageList.toTypedArray())
        }

        processDao.insertAll(*processList.toTypedArray())

        return processList
    }

    private fun parseEntityResult(timelineList: List<TimelineEntity>): List<TimelineEntity> {

        for (timeline in timelineList) {
            val interviewParticipantList = interviewParticipantDao.getInterviewParticipant(timeline.id)
            val feedbackIdList = feedbackIDDao.getFeedback(timeline.id)

            timeline.setInterviewParticipantList(interviewParticipantList)
            timeline.setFeedbackIdList(feedbackIdList)
        }

        return timelineList
    }

    private fun onRetrieveProcessDetailStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveProcessDetailFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveProcessDetailSuccess(process: ProcessEntity) {
        this.process.value = process
        this.isInterviewAdvancer.value = process.isClientInterviewerAdvancer
        this.candidateAvatar.value = process.candidateAvatar
        this.candidateFullName.value = process.candidateFullName
        jobTitle.value = String.format(context.resources.getString(R.string.job_title), process.jobTitle, process.jobCityName)
    }

    private fun onRetrieveTimelineSuccess(timelineList: List<TimelineEntity>) {
       this.timelineList.value = timelineList
    }

    private fun onRetrieveProcessDetailError(e: Throwable) {
        e.printStackTrace()
    }

    private fun onRetrieveProcessSuccess(processList: List<ProcessEntity>) {
        this.loadProcess.value = true
    }

    private fun onRetrieveProcessError(e: Throwable) {
        e.printStackTrace()
    }
}