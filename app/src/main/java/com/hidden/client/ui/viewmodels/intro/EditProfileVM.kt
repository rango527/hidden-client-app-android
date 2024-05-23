package com.hidden.client.ui.viewmodels.intro

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.DashboardApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.isEmailValid
import com.hidden.client.models.json.SimpleResponseJson
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import com.kaopiz.kprogresshud.KProgressHUD
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import javax.inject.Inject

class EditProfileVM(private val context: Context): RootVM()  {
    @Inject
    lateinit var dashboardApi: DashboardApi
    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var progressDlg: KProgressHUD

    private val _navigateSetting = MutableLiveData<Event<Boolean>>()
    val navigateSetting: LiveData<Event<Boolean>>
        get() = _navigateSetting

    // LoginJson form validation
    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean>
        get() = _isFormValid

    var firstName = ""
        set(value) {
            field = value
            validateForm()
        }

    var lastName = ""
        set(value) {
            field = value
            validateForm()
        }

    var email = ""
        set(value) {
            field = value
            validateForm()
        }

    var title = ""
        set(value) {
            field = value
            validateForm()
        }

    private fun validateForm() {
        if (firstName.isNotEmpty()
            && lastName.isNotEmpty()
            && title.isNotEmpty()
            && email.trim().isEmailValid()) {
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

    fun updateProfile() {
        val fullName = "$firstName $lastName"
        subscription = dashboardApi.updateProfile(AppPreferences.apiAccessToken, fullName, email, title).concatMap {
                updateProfileResult -> Observable.just(updateProfileResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onAuthUpdateProfileStart() }
            .doOnTerminate { onAuthUpdateProfileFinish() }
            .subscribe(
                { result -> onAuthUpdateProfileSuccess(result) },
                { error -> onAuthUpdateProfileError(error) }
            )
    }

    fun updateProfileImage(photo: MultipartBody.Part) {
        subscription = dashboardApi.updateProfileImage(AppPreferences.apiAccessToken, photo).concatMap {
                updateProfileImageResult -> Observable.just(updateProfileImageResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onAuthUpdateProfileStart() }
            .doOnTerminate { onAuthUpdateProfileFinish() }
            .subscribe(
                { result -> onAuthUpdateProfileImageSuccess(result) },
                { error -> onAuthUpdateProfileImageError(error) }
            )
    }

    private fun onAuthUpdateProfileStart(){
        loadingVisibility.value = true
    }

    private fun onAuthUpdateProfileFinish(){
        loadingVisibility.value = false
    }

    private fun onAuthUpdateProfileSuccess(result: SimpleResponseJson){
        _navigateSetting.value = Event(true)
    }

    private fun onAuthUpdateProfileError(e: Throwable){
        e.printStackTrace()
        HToast.show(HCGlobal.getInstance().currentActivity, "Not update profile...", HToast.TOAST_ERROR)
    }

    private fun onAuthUpdateProfileImageSuccess(result: SimpleResponseJson){
        updateProfile()
    }

    private fun onAuthUpdateProfileImageError(e: Throwable){
        e.printStackTrace()
        HToast.show(HCGlobal.getInstance().currentActivity, "Not change photo...", HToast.TOAST_ERROR)
    }
}