package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.JobApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.isEmailValid
import com.hidden.client.models.json.SimpleResponseJson
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class JobInviteTeamMemberVM(private val context: Context) : RootVM() {

    @Inject
    lateinit var jobApi: JobApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    // To jump to AddUserRoleActivity
    private val _navigateToAddUserRole = MutableLiveData<Event<Boolean>>()
    val navigateToAddUserRole: LiveData<Event<Boolean>>
        get() = _navigateToAddUserRole

    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean>
        get() = _isFormValid

    var firstName: String = ""
        set(value) {
            field = value
            validateForm()
        }

    var lastName: String = ""
        set(value) {
            field = value
            validateForm()
        }

    var email: String = ""
        set(value) {
            field = value
            validateForm()
        }

    var isUserManager: Boolean = true

    private fun validateForm() {

        if (email.trim().isEmailValid() && firstName.isNotEmpty() && lastName.isNotEmpty()) {
            _isFormValid.postValue(true)
        } else {
            _isFormValid.postValue(false)
        }
    }

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun inviteTeamMember() {
        subscription = jobApi.inviteTeamMember(
            AppPreferences.apiAccessToken,
            firstName,
            lastName,
            email,
            isUserManager
        ).concatMap { apiResult ->
            Observable.just(apiResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onInviteStart() }
            .doOnTerminate { onInviteFinish() }
            .subscribe(
                { result -> onInviteSuccess(result) },
                { error -> onInviteError(error) }
            )
    }

    private fun onInviteStart() {
        loadingVisibility.value = true
    }

    private fun onInviteFinish() {
        loadingVisibility.value = false
    }

    private fun onInviteSuccess(apiResult: SimpleResponseJson) {
        if (apiResult.errors.isEmpty()) {
            _navigateToAddUserRole.value = Event(true)
        } else {
            HToast.show(HCGlobal.getInstance().currentActivity, apiResult.errors[0], HToast.TOAST_ERROR)
        }
    }

    private fun onInviteError(e: Throwable) {
        HToast.show(HCGlobal.getInstance().currentActivity, "User already exists", HToast.TOAST_ERROR)
        e.printStackTrace()
    }
}