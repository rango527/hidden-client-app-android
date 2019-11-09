package com.hidden.client.viewmodels.intro

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.LoginAPI
import com.hidden.client.models.Login
import com.hidden.client.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginVM(private val email: String, private val password: String): RootVM() {

    @Inject
    lateinit var loginApi: LoginAPI

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { authLogin() }

    private lateinit var subscription: Disposable

    init {
        authLogin()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun authLogin(){
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
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveAuthLoginFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveAuthLoginSuccess(loginResult: Login){

    }

    private fun onRetrieveAuthLoginError(){
        errorMessage.value = R.string.login_error
    }
}