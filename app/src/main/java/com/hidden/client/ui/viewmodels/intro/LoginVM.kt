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
import com.hidden.client.models.json.LoginJson
import com.hidden.client.models.json.SimpleResponseJson
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import com.kaopiz.kprogresshud.KProgressHUD
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginVM(private val context: Context): RootVM() {

    @Inject
    lateinit var loginApi: LoginApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val loadingResetPasswordVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var progressDlg: KProgressHUD

    // To jump to HomeActivity after login success
    private val _navigateToHome = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<Boolean>>
        get() = _navigateToHome

    // To jump to HomeActivity after login success
    private val _navigateSendMessageHome = MutableLiveData<Event<Boolean>>()
    val navigateSendMessageHome: LiveData<Event<Boolean>>
        get() = _navigateSendMessageHome

    // LoginJson form validation
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

    private fun validateForm() {
        if (email.isEmailValid() && password.length >= User.passwordMinLength) {
            _isFormValid.postValue(true)
        } else {
            _isFormValid.postValue(false)
        }
    }

    // ResetPasswordJson form validation
    private val _isResetFormValid = MutableLiveData<Boolean>()
    val isResetFormValid: LiveData<Boolean>
        get() = _isResetFormValid

    var resetEmail = ""
        set(value) {
            field = value
            validateResetForm()
        }

    private fun validateResetForm() {
        if (resetEmail.isEmailValid()) {
            _isResetFormValid.postValue(true)
        } else {
            _isResetFormValid.postValue(false)
        }
    }

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun authLogin() {
        subscription = loginApi.clientLogin(email, password).concatMap {
                        loginResult -> Observable.just(loginResult)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onAuthLoginStart() }
            .doOnTerminate { onAuthLoginFinish() }
            .subscribe(
                { result -> onAuthLoginSuccess(result) },
                { error -> onAuthLoginError(error) }
            )
    }

    fun resetPassword() {
        subscription = loginApi.sendPasswordRequest(resetEmail).concatMap {
                resetPasswordResult -> Observable.just(resetPasswordResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onResetPasswordStart() }
            .doOnTerminate { onResetPasswordFinish() }
            .subscribe(
                { result -> onResetPasswordSuccess(result) },
                { error -> onResetPasswordError(error) }
            )
    }

    private fun onAuthLoginStart(){
        loadingVisibility.value = true
    }

    private fun onAuthLoginFinish(){
        loadingVisibility.value = false
    }

    private fun onResetPasswordStart(){
        loadingResetPasswordVisibility.value = true
    }

    private fun onResetPasswordFinish(){
        loadingResetPasswordVisibility.value = false
    }

    private fun onAuthLoginSuccess(loginResult: LoginJson){
        AppPreferences.myId = loginResult.clientId.safeValue()
        AppPreferences.isUserManager = loginResult.isUserManager.safeValue()
        AppPreferences.myFullName = loginResult.fullName.safeValue()
        AppPreferences.apiAccessToken = """Bearer ${loginResult.token}"""

        _navigateToHome.value = Event(true)
    }

    private fun onAuthLoginError(e: Throwable){
        e.printStackTrace()
        HToast.show(HCGlobal.getInstance().currentActivity, "Wrong email and password", HToast.TOAST_ERROR)
    }

    private fun onResetPasswordSuccess(result: SimpleResponseJson){
        _navigateSendMessageHome.value = Event(true)
    }

    private fun onResetPasswordError(e: Throwable){
        e.printStackTrace()
        val mess = "$resetEmail: User not found"
        HToast.show(HCGlobal.getInstance().currentActivity, mess, HToast.TOAST_ERROR)
    }
}