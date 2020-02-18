package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCGlobal
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
import javax.inject.Inject

class ProcessAddUserRoleVM(
    private val context: Context
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    // To jump to ProcessSetting after add new user role
    private val _navigateToProcessSetting = MutableLiveData<Event<Boolean>>()
    val navigateToProcessSetting: LiveData<Event<Boolean>>
        get() = _navigateToProcessSetting

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val roleAvailableUserListAdapter: RoleAvailableUserListAdapter = RoleAvailableUserListAdapter()

    var search = ""
        set(value) {
            field = value

            val tempRoleAvailableUserList: ArrayList<RoleAvailableUser> = arrayListOf()

            for ((index, userManager) in roleAvailableUserList.withIndex()) {
                if (userManager.user.fullName.contains(value, true)) {
                    tempRoleAvailableUserList.add(userManager)
                } else {
                    roleAvailableUserList[index].show = false
                }
            }
            roleAvailableUserListAdapter.updateUserManagerList(tempRoleAvailableUserList)
        }

    var roleAvailableUserList: ArrayList<RoleAvailableUser> = arrayListOf()

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadAvailableUsers(processId: Int, role: String) {

        subscription = processApi.getAvailableUsersToAddRole(AppPreferences.apiAccessToken, processId, role).concatMap {
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

    fun addUserRoleToProcessSetting(processId: Int, role: String, clientIds: ArrayList<Int>) {
        HCGlobal.getInstance().log(role)
        subscription = processApi.addUserRoleJobSetting(AppPreferences.apiAccessToken, processId, role, clientIds).concatMap {
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
            userList.add(reviewer.toEntity(0, Enums.SettingType.PROCESS.value, 0, 0))
        }

        val tempRoleAvailableUserList: ArrayList<RoleAvailableUser> = arrayListOf()

        for (user in userList) {
            tempRoleAvailableUserList.add(RoleAvailableUser(user, false, show = true))
        }

        roleAvailableUserList = tempRoleAvailableUserList

        roleAvailableUserListAdapter.updateUserManagerList(tempRoleAvailableUserList)
    }

    fun onAddRoleSuccess(result: SimpleResponseJson) {
        _navigateToProcessSetting.value = Event(true)
    }

    private fun onRetrieveAvailableUserError(e: Throwable) {
        e.printStackTrace()
    }
}