package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.dao.ProcessDao
import com.hidden.client.models.dao.ProcessStageDao
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.models.entity.ProcessStageEntity
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProcessDetailVM(
    private val context: Context,
    private val processDao: ProcessDao,
    private val processStageDao: ProcessStageDao
) : RootVM() {

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    var process: MutableLiveData<ProcessEntity> = MutableLiveData()

    val candidateAvatar: MutableLiveData<String> = MutableLiveData<String>("")
    val candidateFullName: MutableLiveData<String> = MutableLiveData<String>("")
    val jobTitle: MutableLiveData<String> = MutableLiveData<String>("")

    private var subscription: Disposable? = null

    var processId: Int = 0

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadProcessDetail() {

        subscription = Observable.fromCallable {
            processDao.getProcessById(processId)
        }
            .concatMap { dbProcess ->
                val process = dbProcess[0]
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


    private fun onRetrieveProcessDetailStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveProcessDetailFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveProcessDetailSuccess(process: ProcessEntity) {
        this.process.value = process

        this.candidateAvatar.value = process.candidateAvatar
        this.candidateFullName.value = process.candidateFullName
        jobTitle.value = String.format(context.resources.getString(R.string.job_title), process.jobTitle, process.jobCityName)
    }

    private fun onRetrieveProcessDetailError(e: Throwable) {
        e.printStackTrace()
    }

}