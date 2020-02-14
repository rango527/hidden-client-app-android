package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.JobApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.custom.UserManager
import com.hidden.client.models.dao.JobSettingDao
import com.hidden.client.models.dao.ReviewerDao
import com.hidden.client.models.entity.JobSettingEntity
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.models.json.JobSettingJson
import com.hidden.client.models.json.ReviewerJson
import com.hidden.client.models.json.SimpleResponseJson
import com.hidden.client.ui.adapters.ReviewerListAdapter
import com.hidden.client.ui.adapters.UserManagerListAdapter
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class JobAddUserRoleVM(
    private val context: Context
) : RootVM() {

    @Inject
    lateinit var jobApi: JobApi

    // To jump to HomeActivity after login success
    private val _navigateToJobSetting = MutableLiveData<Event<Boolean>>()
    val navigateToJobSetting: LiveData<Event<Boolean>>
        get() = _navigateToJobSetting

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val userManagerListAdapter: UserManagerListAdapter = UserManagerListAdapter()

    var search = ""
        set(value) {
            field = value

            val tempUserManagerList: ArrayList<UserManager> = arrayListOf()

            for ((index, userManager) in userManagerList.withIndex()) {
                if (userManager.user.fullName.contains(value, true)) {
                    tempUserManagerList.add(userManager)
                } else {
                    userManagerList[index].show = false
                }
            }
            userManagerListAdapter.updateUserManagerList(tempUserManagerList)
        }

    var userManagerList: ArrayList<UserManager> = arrayListOf()

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadAvailableUsers(jobId: Int, role: String) {

        subscription = jobApi.getAvailableUsersToAddRole(AppPreferences.apiAccessToken, jobId, role).concatMap {
                apiResult -> Observable.just(apiResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveAvailableUserStart() }
            .doOnTerminate { onRetrieveAvailableUserFinish() }
            .subscribe(
                { result -> onRetrieveAvailableUserSuccess(result) },
                { error -> onRetrieveAvailableUserError(error) }
            )
    }

    fun addUserRoleToJobSetting(jobId: Int, role: String, clientIds: ArrayList<Int>, cascade: Boolean) {
        subscription = jobApi.addUserRoleJobSetting(AppPreferences.apiAccessToken, jobId, role, clientIds, cascade).concatMap {
                addResult -> Observable.just(addResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveAvailableUserStart() }
            .doOnTerminate { onRetrieveAvailableUserFinish() }
            .subscribe(
                { result -> onAddRoleSuccess(result) },
                { error -> onRetrieveAvailableUserError(error) }
            )
    }

    private fun onRetrieveAvailableUserStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveAvailableUserFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveAvailableUserSuccess(json: List<ReviewerJson>) {

        val userList: ArrayList<ReviewerEntity> = arrayListOf()

        for (reviewer in json) {
            userList.add(reviewer.toEntity(0, 0, 0))
        }

        val tempUserManagerList: ArrayList<UserManager> = arrayListOf()

        for (user in userList) {
            tempUserManagerList.add(UserManager(user, false, show = true))
        }

        userManagerList = tempUserManagerList

        userManagerListAdapter.updateUserManagerList(tempUserManagerList)
    }

    fun onAddRoleSuccess(result: SimpleResponseJson) {
        _navigateToJobSetting.value = Event(true)
    }

    private fun onRetrieveAvailableUserError(e: Throwable) {
        e.printStackTrace()
    }
}