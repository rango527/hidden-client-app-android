package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.models.custom.RoleAvailableUser
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.models.json.ReviewerJson
import com.hidden.client.models.json.SimpleResponseJson
import com.hidden.client.ui.adapters.RoleAvailableUserListAdapter
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import javax.inject.Inject

class ProcessTabVM(
    private val context: Context
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    // To jump to Process Detail Activity after login success
    private val _navigateToProcess = MutableLiveData<Event<Boolean>>()
    val navigateToProcess: LiveData<Event<Boolean>>
        get() = _navigateToProcess

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val availableInterviewerListAdapter: RoleAvailableUserListAdapter =
        RoleAvailableUserListAdapter()
    var availableInterviewerList: ArrayList<RoleAvailableUser> = arrayListOf()

    var search = ""
        set(value) {
            field = value

            val tempAvailableInterviewerList: ArrayList<RoleAvailableUser> = arrayListOf()

            for ((index, userManager) in availableInterviewerList.withIndex()) {
                if (userManager.user.fullName.contains(value, true)) {
                    tempAvailableInterviewerList.add(userManager)
                } else {
                    tempAvailableInterviewerList[index].show = false
                }
            }
            availableInterviewerListAdapter.updateUserManagerList(tempAvailableInterviewerList)
        }


    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadAvailableInterviewerList(processId: Int, interviewId: Int) {

        subscription = processApi.getAvailableInterviewersForProcess(
            AppPreferences.apiAccessToken,
            processId,
            interviewId
        ).concatMap { apiResult ->
            Observable.just(apiResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveAvailableInterviewerStart() }
            .doOnTerminate { onRetrieveAvailableInterviewerFinish() }
            .subscribe(
                { result -> onRetrieveAvailableInterviewerSuccess(result) },
                { error -> onRetrieveAvailableInterviewerError(error) }
            )
    }

    fun addInterviewersToInterview(
        processId: Int,
        interviewId: Int,
        body: RequestBody
    ) {
        subscription = processApi.addInterviewersToInterview(
            "application/json",
            AppPreferences.apiAccessToken,
            processId,
            interviewId,
            body
        ).concatMap { addResult ->
            Observable.just(addResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveAvailableInterviewerStart() }
            .doOnTerminate { onRetrieveAvailableInterviewerFinish() }
            .subscribe(
                { result -> onAddInterviewerSuccess(result) },
                { error -> onRetrieveAvailableInterviewerError(error) }
            )
    }

    private fun onRetrieveAvailableInterviewerStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveAvailableInterviewerFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveAvailableInterviewerSuccess(json: List<ReviewerJson>) {

        val userList: ArrayList<ReviewerEntity> = arrayListOf()

        for (reviewer in json) {
            userList.add(reviewer.toEntity(0, Enums.SettingType.JOB.value, 0, 0))
        }

        val tempAvailableInterviewerList: ArrayList<RoleAvailableUser> = arrayListOf()

        for (user in userList) {
            tempAvailableInterviewerList.add(RoleAvailableUser(user, false, show = true))
        }

        availableInterviewerList = tempAvailableInterviewerList

        availableInterviewerListAdapter.updateUserManagerList(tempAvailableInterviewerList)
    }

    fun onAddInterviewerSuccess(result: SimpleResponseJson) {
        _navigateToProcess.value = Event(true)
    }

    private fun onRetrieveAvailableInterviewerError(e: Throwable) {
        e.printStackTrace()
    }
}