package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.custom.GetAllJob
import com.hidden.client.models.custom.JobPick
import com.hidden.client.models.dao.ProcessDao
import com.hidden.client.models.dao.ProcessStageDao
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.models.json.ProcessJson
import com.hidden.client.ui.adapters.JobFilterAdapter
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class JobListVM(
    private val processDao: ProcessDao,
    private val processStageDao: ProcessStageDao
) : RootVM() {

    @Inject
    internal lateinit var processApi: ProcessApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    var jobListAdapter: JobFilterAdapter = JobFilterAdapter()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()

    private var subscription: Disposable? = null
    var search = 0
        set(value) {
            field = value
            loadJob(true)
        }

    init {
        loadJob(false)
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadJob(cashMode: Boolean) {
        lateinit var apiObservable: Observable<List<ProcessEntity>>

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

    private fun parseJsonResult(jsonList: List<ProcessJson>): List<ProcessEntity> {
        processDao.deleteAll()
        processStageDao.deleteAll()

        val processList: ArrayList<ProcessEntity> = arrayListOf()

        var index = 0
        HCGlobal.getInstance().getAllJobList.clear()
        HCGlobal.getInstance().getJobPick.clear()

        for (json in jsonList) {
            val process: ProcessEntity = json.toEntity(AppPreferences.myId)
            val processStageList = json.toStageEntityList(process.id)

            process.setStageList(processStageList)
            // get all job id, title and city name
            for (x in 0 until index + 1) {
                if (HCGlobal.getInstance().getAllJobList.size > x) {
                    if (HCGlobal.getInstance().getAllJobList[x].jobId == json.jobId) {
                        break
                    }
                } else {
                    val getJobList = GetAllJob()
                    val getJobPick = JobPick()
                    getJobList.jobId = json.jobId!!
                    getJobList.jobTitle = json.jobTitle.toString()
                    getJobList.jobCityName = json.jobCityName.toString()
                    getJobList.jobTick = false
                    getJobPick.jobTick = false
                    HCGlobal.getInstance().getAllJobList.add(getJobList)
                    HCGlobal.getInstance().getJobPick.add(getJobPick)
                    processList.add(process)
                    break
                }
            }
            index += 1
            // end get all job id..

            processStageDao.insertAll(*processStageList.toTypedArray())
        }

        processDao.insertAll(*processList.toTypedArray())

        return processList
    }

    private fun parseEntityResult(processList: List<ProcessEntity>): List<ProcessEntity> {
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

    private fun onRetrieveProcessSuccess(processList: List<ProcessEntity>) {
        jobListAdapter.updateProcessList(processList)
    }

    private fun onRetrieveProcessError(e: Throwable) {
        e.printStackTrace()
    }
}