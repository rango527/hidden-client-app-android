package com.hidden.client.ui.viewmodels.intro

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.LoginApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.User
import com.hidden.client.helpers.extension.isEmailValid
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.json.AcceptInviteJson
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignUpVM(private val context: Context): RootVM() {

    @Inject
    lateinit var loginApi: LoginApi
    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    // To jump to HomeActivity after SignUp success
    private val _navigateToHome = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<Boolean>>
        get() = _navigateToHome

    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean>
        get() = _isFormValid

    var email = ""
        set(value) {
            field = value
            validateForm()
        }

    var password = ""
        set(value) {
            field = value
            validateForm()
        }

    var code = ""
        set(value) {
            field = value
            validateForm()
        }

    private fun validateForm() {
        if (email.trim().isEmailValid() && password.length >= User.passwordMinLength && code.length >= User.inviteCodeLength) {
            _isFormValid.postValue(true)
        } else {
            _isFormValid.postValue(false)
        }
    }

    private val _isCheckedTermsValid = MutableLiveData<Boolean>()
    val isCheckedTermsValid: LiveData<Boolean>
        get() = _isCheckedTermsValid

    var isCheckedTerms: Boolean = false
        set(value) {
            field = value
            validateChecked()
        }

    private fun validateChecked() {
        if (isCheckedTerms) {
            _isCheckedTermsValid.postValue(true)
        } else {
            _isCheckedTermsValid.postValue(false)
        }
    }

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun signUp(email: String, password: String, code: String, meta: String) {
        subscription = loginApi.acceptInvite(email, password, code, meta).concatMap {
                acceptInviteResult -> Observable.just(acceptInviteResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onAcceptInviteStart() }
            .doOnTerminate { onAcceptInviteFinish() }
            .subscribe(
                { result -> onAcceptInviteSuccess(result) },
                { error -> onAcceptInviteError(error) }
            )
    }

    private fun onAcceptInviteStart(){
        loadingVisibility.value = true
    }

    private fun onAcceptInviteFinish(){
        loadingVisibility.value = false
    }

    private fun onAcceptInviteSuccess(acceptInviteResult: AcceptInviteJson){
        AppPreferences.myId = acceptInviteResult.clientId.safeValue()
        AppPreferences.myFullName = acceptInviteResult.fullName.safeValue()
        AppPreferences.apiAccessToken = """Bearer ${acceptInviteResult.token}"""

        _navigateToHome.value = Event(true)
    }

    private fun onAcceptInviteError(e: Throwable){
        e.printStackTrace()
        HToast.show(HCGlobal.getInstance().currentActivity, "Wrong token or token already used", HToast.TOAST_ERROR)
    }

}