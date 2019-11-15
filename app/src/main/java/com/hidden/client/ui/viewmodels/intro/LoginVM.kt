package com.hidden.client.ui.viewmodels.intro

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.LoginApi
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.User
import com.hidden.client.helpers.extension.isEmailValid
import com.hidden.client.models.Login
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

    private lateinit var subscription: Disposable

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun authLogin() {
        subscription = Observable.fromCallable { }
            .concatMap {
                loginApi.clientLogin(email, password).concatMap {
                    loginResult -> Observable.just(loginResult)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveAuthLoginStart() }
            .doOnTerminate { onRetrieveAuthLoginFinish() }
            .subscribe(
                { result -> onRetrieveAuthLoginSuccess(result) },
                { onRetrieveAuthLoginError() }
            )
    }

    private fun onRetrieveAuthLoginStart(){
        loadingVisibility.value = true
        errorMessage.value = null
    }

    private fun onRetrieveAuthLoginFinish(){
        loadingVisibility.value = false
    }

    private fun onRetrieveAuthLoginSuccess(loginResult: Login){
        HCGlobal.getInstance().log("hello");
        HCGlobal.getInstance().log(loginResult.fullName);
    }

    private fun onRetrieveAuthLoginError(){
        HCGlobal.getInstance().log("failed");
        errorMessage.value = R.string.login_error
    }
}