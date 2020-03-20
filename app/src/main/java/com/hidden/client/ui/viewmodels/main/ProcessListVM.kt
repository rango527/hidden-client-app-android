package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.models.dao.ProcessDao
import com.hidden.client.models.dao.ProcessStageDao
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.models.json.ProcessJson
import com.hidden.client.ui.adapters.ProcessListAdapter
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProcessListVM(
    private val processDao: ProcessDao,
    private val processStageDao: ProcessStageDao
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val processListAdapter: ProcessListAdapter = ProcessListAdapter()

    private var subscription: Disposable? = null

    init {
        loadProcess(false)
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadProcess(cashMode: Boolean) {

        val apiObservable: Observable<List<ProcessEntity>>

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
        processListAdapter.updateProcessList(processList)
    }

    private fun onRetrieveProcessError(e: Throwable) {
        e.printStackTrace()
    }
}