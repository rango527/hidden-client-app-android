package com.hidden.client.ui.viewmodels.intro

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.LoginApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.User
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

class ResetPasswordVM(private val context: Context): RootVM()  {
    @Inject
    lateinit var loginApi: LoginApi
    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var progressDlg: KProgressHUD

    // To jump to SettingFragment after reset password success
    private val _navigateSetting = MutableLiveData<Event<Boolean>>()
    val navigateSetting: LiveData<Event<Boolean>>
        get() = _navigateSetting

    private val _navigateNewPassword = MutableLiveData<Event<Boolean>>()
    val navigateNewPassword: LiveData<Event<Boolean>>
        get() = _navigateNewPassword

    // LoginJson form validation
    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean>
        get() = _isFormValid

    private val _isNewPasswordValid = MutableLiveData<Boolean>()
    val isNewPasswordValid: LiveData<Boolean>
        get() = _isNewPasswordValid

    var currentPassword = ""
        set(value) {
            field = value
            validateForm()
        }

    var newPassword = ""
        set(value) {
            field = value
            validateForm()
        }

    var newPasswordOnceMore = ""
        set(value) {
            field = value
            validateForm()
        }

    private fun validateForm() {
        if (currentPassword.length >= User.passwordMinLength
            && newPassword.length >= User.passwordMinLength
            && newPasswordOnceMore.length >= User.passwordMinLength
            && newPassword == newPasswordOnceMore) {
            _isFormValid.postValue(true)
        } else {
            _isFormValid.postValue(false)
        }

        if (newPassword.length >= User.passwordMinLength
            && newPasswordOnceMore.length >= User.passwordMinLength
            && newPassword == newPasswordOnceMore) {
            _isNewPasswordValid.postValue(true)
        } else {
            _isNewPasswordValid.postValue(false)
        }
    }

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun authResetPassword() {
        subscription = loginApi.resetPassword(AppPreferences.apiAccessToken, currentPassword, newPassword).concatMap {
                resetPasswordResult -> Observable.just(resetPasswordResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onAuthResetPasswordStart() }
            .doOnTerminate { onAuthResetPasswordFinish() }
            .subscribe(
                { result -> onAuthResetPasswordSuccess(result) },
                { error -> onAuthResetPasswordError(error) }
            )
    }

    fun changeForgottenPassword(email: String, token: String) {
        subscription = loginApi.changeForgottenPassword(email, token, newPassword).concatMap {
                changeForgottenPasswordResult -> Observable.just(changeForgottenPasswordResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onAuthResetPasswordStart() }
            .doOnTerminate { onAuthResetPasswordFinish() }
            .subscribe(
                { result -> onChangeForgottenPasswordSuccess(result) },
                { error -> onChangeForgottenPasswordError(error) }
            )
    }

    private fun onAuthResetPasswordStart(){
        loadingVisibility.value = true
    }

    private fun onAuthResetPasswordFinish(){
        loadingVisibility.value = false
    }

    private fun onAuthResetPasswordSuccess(result: SimpleResponseJson){
        _navigateSetting.value = Event(true)
    }

    private fun onChangeForgottenPasswordSuccess(result: SimpleResponseJson){
        _navigateNewPassword.value = Event(true)
        HToast.show(HCGlobal.getInstance().currentActivity, "New password set!", HToast.TOAST_SUCCESS)
    }

    private fun onAuthResetPasswordError(e: Throwable){
        e.printStackTrace()
        HToast.show(HCGlobal.getInstance().currentActivity, "Wrong password", HToast.TOAST_ERROR)
    }

    private fun onChangeForgottenPasswordError(e: Throwable){
        e.printStackTrace()
        HToast.show(HCGlobal.getInstance().currentActivity, "Can't reset password", HToast.TOAST_ERROR)
    }
}