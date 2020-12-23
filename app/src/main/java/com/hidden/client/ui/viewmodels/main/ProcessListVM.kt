package com.hidden.client.ui.viewmodels.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.custom.GetAllJob
import com.hidden.client.models.custom.RoleAvailableUser
import com.hidden.client.models.dao.ProcessDao
import com.hidden.client.models.dao.ProcessStageDao
import com.hidden.client.models.json.ProcessJson
import com.hidden.client.ui.fragments.home.processes.ProcessesFragment
import com.hidden.client.ui.adapters.ProcessListAdapter
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import com.hidden.client.models.entity.ProcessEntity as ProcessEntity1

class ProcessListVM(
    private val processDao: ProcessDao,
    private val processStageDao: ProcessStageDao
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val processListCount: MutableLiveData<Boolean> = MutableLiveData()
    val processList: MutableLiveData<List<ProcessEntity1>> = MutableLiveData()

    val processListAdapter: ProcessListAdapter = ProcessListAdapter()
    val processesFragment: ProcessesFragment = ProcessesFragment(true)

    private var subscription: Disposable? = null

//    init {
//        loadProcess(false)
//    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadProcess(cashMode: Boolean) {
        val apiObservable: Observable<List<ProcessEntity1>>

        if (cashMode) {
            apiObservable =
                Observable.fromCallable { processDao.getProcess(AppPreferences.myId) }
                    .concatMap { dbProcessData ->
                        if (dbProcessData.isEmpty()) {
                            processApi.getProcessList(AppPreferences.apiAccessToken)
                                .concatMap { apiProcess ->
                                    Observable.just(parseJsonResult(apiProcess))
                                }
                        } else {
                            Observable.just(parseEntityResult(dbProcessData))
                        }
                    }
        } else {
            apiObservable = Observable.fromCallable { }
                .concatMap {
                    processApi.getProcessList(AppPreferences.apiAccessToken)
                        .concatMap { apiProcess ->
                            Observable.just(parseJsonResult(apiProcess))
                        }
                }
        }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveProcessStart() }
            .doOnTerminate { onRetrieveProcessFinish() }
            .subscribe(
                { result -> onRetrieveProcessSuccess(result) },
                { error -> onRetrieveProcessError(error) }
            )
    }

    private fun parseJsonResult(jsonList: List<ProcessJson>): List<ProcessEntity1> {

        processDao.deleteAll()
        processStageDao.deleteAll()

        val processList: ArrayList<ProcessEntity1> = arrayListOf()

        var index = 0

        for (json in jsonList) {
            val process: ProcessEntity1 = json.toEntity(AppPreferences.myId)
            val processStageList = json.toStageEntityList(process.id)

            process.setStageList(processStageList)

            //filter job id
            val filterJobList = HCGlobal.getInstance().getAllJobList
            val filterProcessList = HCGlobal.getInstance().currentProcessFilterList
            if (!processesFragment.isJobFilterResult() && !processesFragment.isProcessFilterResult()) {
                processList.add(process)
            } else if (processesFragment.isJobFilterResult()) {
                for (x in 0 until filterJobList.size) {
                    if (filterJobList[x].jobTick && json.jobId == filterJobList[x].jobId) {
                        if (processesFragment.isProcessFilterResult()) {
                            when (HCGlobal.getInstance().currentProcessFilterList.currentReadStatus) {
                                0 -> {
                                    if (json.messageUnreadNumber == 0){
                                        if (filterProcessList.currentShortlistStage && json.stages?.get(0)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentFirstStage && json.stages?.get(1)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentFurtherStage && json.stages?.get(2)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentFinalStage && json.stages?.get(3)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentOfferStage && json.stages?.get(4)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentOfferAccepted && json.stages?.get(5)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentStarted && json.stages?.get(6)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                    }
                                }
                                1 -> {
                                    if (json.messageUnreadNumber!! >= 1) {
                                        if (filterProcessList.currentShortlistStage && json.stages?.get(0)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentFirstStage && json.stages?.get(1)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentFurtherStage && json.stages?.get(2)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentFinalStage && json.stages?.get(3)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentOfferStage && json.stages?.get(4)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentOfferAccepted && json.stages?.get(5)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                        if (filterProcessList.currentStarted && json.stages?.get(6)?.stageStatus == "CURRENT")
                                            processList.add(process)
                                    }
                                }
                                else -> {
                                    if (filterProcessList.currentShortlistStage && json.stages?.get(0)?.stageStatus == "CURRENT")
                                        processList.add(process)
                                    if (filterProcessList.currentFirstStage && json.stages?.get(1)?.stageStatus == "CURRENT")
                                        processList.add(process)
                                    if (filterProcessList.currentFurtherStage && json.stages?.get(2)?.stageStatus == "CURRENT")
                                        processList.add(process)
                                    if (filterProcessList.currentFinalStage && json.stages?.get(3)?.stageStatus == "CURRENT")
                                        processList.add(process)
                                    if (filterProcessList.currentOfferStage && json.stages?.get(4)?.stageStatus == "CURRENT")
                                        processList.add(process)
                                    if (filterProcessList.currentOfferAccepted && json.stages?.get(5)?.stageStatus == "CURRENT")
                                        processList.add(process)
                                    if (filterProcessList.currentStarted && json.stages?.get(6)?.stageStatus == "CURRENT")
                                        processList.add(process)
                                }
                            }
                        } else {
                            processList.add(process)
                        }
                    }
                }
            } else if (processesFragment.isProcessFilterResult()){
                when (HCGlobal.getInstance().currentProcessFilterList.currentReadStatus) {
                    0 -> {
                        if (json.messageUnreadNumber == 0){
                            if (filterProcessList.currentShortlistStage && json.stages?.get(0)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentFirstStage && json.stages?.get(1)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentFurtherStage && json.stages?.get(2)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentFinalStage && json.stages?.get(3)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentOfferStage && json.stages?.get(4)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentOfferAccepted && json.stages?.get(5)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentStarted && json.stages?.get(6)?.stageStatus == "CURRENT")
                                processList.add(process)
                        }
                    }
                    1 -> {
                        if (json.messageUnreadNumber!! >= 1) {
                            if (filterProcessList.currentShortlistStage && json.stages?.get(0)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentFirstStage && json.stages?.get(1)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentFurtherStage && json.stages?.get(2)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentFinalStage && json.stages?.get(3)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentOfferStage && json.stages?.get(4)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentOfferAccepted && json.stages?.get(5)?.stageStatus == "CURRENT")
                                processList.add(process)
                            if (filterProcessList.currentStarted && json.stages?.get(6)?.stageStatus == "CURRENT")
                                processList.add(process)
                        }
                    }
                    else -> {
                        if (filterProcessList.currentShortlistStage && json.stages?.get(0)?.stageStatus == "CURRENT")
                            processList.add(process)
                        if (filterProcessList.currentFirstStage && json.stages?.get(1)?.stageStatus == "CURRENT")
                            processList.add(process)
                        if (filterProcessList.currentFurtherStage && json.stages?.get(2)?.stageStatus == "CURRENT")
                            processList.add(process)
                        if (filterProcessList.currentFinalStage && json.stages?.get(3)?.stageStatus == "CURRENT")
                            processList.add(process)
                        if (filterProcessList.currentOfferStage && json.stages?.get(4)?.stageStatus == "CURRENT")
                            processList.add(process)
                        if (filterProcessList.currentOfferAccepted && json.stages?.get(5)?.stageStatus == "CURRENT")
                            processList.add(process)
                        if (filterProcessList.currentStarted && json.stages?.get(6)?.stageStatus == "CURRENT")
                            processList.add(process)
                    }
                }
            }

            processStageDao.insertAll(*processStageList.toTypedArray())
        }

        processDao.insertAll(*processList.toTypedArray())

        val tempList: ArrayList<ProcessEntity1> = arrayListOf()

        when (HCGlobal.getInstance().currentProcessFilterList.currentSortBy) {
            0 -> {
                for (x in 0 until processList.size - 1) {
                    for (y in x + 1 until processList.size) {
                        if (processList[y].lastMessageCreatedAt > processList[x].lastMessageCreatedAt) {
                            tempList.clear()
                            tempList.add(processList[x])
                            processList[x] = processList[y]
                            processList[y] = tempList[0]
                        }
                    }
                }
            }
            1 -> {
                for (x in 0 until processList.size - 1) {
                    for (y in x + 1 until processList.size) {
                        if (processList[y].statusId > processList[x].statusId) {
                            tempList.clear()
                            tempList.add(processList[x])
                            processList[x] = processList[y]
                            processList[y] = tempList[0]
                        }
                    }
                }
            }
            2 -> {
                for (x in 0 until processList.size - 1) {
                    for (y in x + 1 until processList.size) {
                        if (processList[y].getStageList()[0].stageCreatedAt > processList[x].getStageList()[0].stageCreatedAt) {
                            tempList.clear()
                            tempList.add(processList[x])
                            processList[x] = processList[y]
                            processList[y] = tempList[0]
                        }
                    }
                }
            }
        }

        return processList
    }

    private fun parseEntityResult(processList: List<ProcessEntity1>): List<ProcessEntity1> {
        for (process in processList) {
            val processStageList = processStageDao.getStageByProcess(process.id)
            process.setStageList(processStageList)
        }

        return processList
    }

    private fun onRetrieveProcessStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveProcessFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveProcessSuccess(processList: List<ProcessEntity1>) {
        processListCount.value = processList.isEmpty()
//        this.processList.value = processList
        processListAdapter.updateProcessList(processList)
    }

    private fun onRetrieveProcessError(e: Throwable) {
        e.printStackTrace()
    }
}