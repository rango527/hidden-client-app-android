package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.dao.ProcessDao
import com.hidden.client.models.dao.ProcessStageDao
import com.hidden.client.models.entity.DashboardTileEntity
import com.hidden.client.models.entity.ReviewerEntity
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
    private val context: Context,
    private val processDao: ProcessDao,
    private val processStageDao: ProcessStageDao
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val processListCount: MutableLiveData<Boolean> = MutableLiveData()
    val processList: MutableLiveData<List<ProcessEntity1>> = MutableLiveData()
    val processListVM: MutableLiveData<List<ProcessEntity1>> = MutableLiveData()

    val processListAdapter: ProcessListAdapter = ProcessListAdapter()
    private val processesFragment: ProcessesFragment = ProcessesFragment(true)

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

    fun parseJsonResult(jsonList: List<ProcessJson>): List<ProcessEntity1> {

        processDao.deleteAll()
        processStageDao.deleteAll()

        val processList: ArrayList<ProcessEntity1> = arrayListOf()

        for (json in jsonList) {
            val process: ProcessEntity1 = json.toEntity(AppPreferences.myId)
            val processStageList = json.toStageEntityList(process.id)

            process.setStageList(processStageList)
            processList.add(process)
            processStageDao.insertAll(*processStageList.toTypedArray())
        }

        processDao.insertAll(*processList.toTypedArray())

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

        this.processListVM.value = processList

        processListAdapter.updateProcessList(processList)
    }

    private fun onRetrieveProcessError(e: Throwable) {
        e.printStackTrace()
    }
}