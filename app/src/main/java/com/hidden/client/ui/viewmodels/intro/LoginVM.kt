package com.hidden.client.ui.viewmodels.intro

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.LoginApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.User
import com.hidden.client.helpers.extension.isEmailValid
import com.hidden.client.models.json.LoginJson
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginVM: RootVM() {

    @Inject
    lateinit var loginApi: LoginApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { authLogin() }

    // To jump to HomeActivity after login success
    private val _navigateToHome = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<Boolean>>
        get() = _navigateToHome

    // LoginJson form validation
    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean>
        get() = _isFormValid

    var email = "test1@test.com"
        set(value) {
            field = value
            validateForm()
        }

    var password = "test"
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

    private lateinit var subscription: Disposable

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun authLogin() {
        subscription =loginApi.clientLogin(email, password).concatMap {
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

    private fun onAuthLoginStart(){
        loadingVisibility.value = true
        errorMessage.value = null
    }

    private fun onAuthLoginFinish(){
        loadingVisibility.value = false
    }

    private fun onAuthLoginSuccess(loginResult: LoginJson){
        if (loginResult.status == User.approved) {
            AppPreferences.myId = loginResult.clientId!!
            AppPreferences.myFullName = loginResult.fullName!!
            AppPreferences.apiAccessToken = """Bearer ${loginResult.token}"""

            _navigateToHome.value = Event(true)
        }

    }

    private fun onAuthLoginError(e: Throwable){
        e.printStackTrace()
    }
}