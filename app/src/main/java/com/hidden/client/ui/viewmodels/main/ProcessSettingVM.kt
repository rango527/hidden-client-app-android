package com.hidden.client.ui.viewmodels.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.ProcessApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.dao.ProcessSettingDao
import com.hidden.client.models.dao.ReviewerDao
import com.hidden.client.models.entity.ProcessSettingEntity
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.models.json.ProcessSettingJson
import com.hidden.client.ui.adapters.ReviewerListAdapter
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProcessSettingVM (
    private val context: Context,
    private val processSettingDao: ProcessSettingDao,
    private val reviewerDao: ReviewerDao
) : RootVM() {

    @Inject
    lateinit var processApi: ProcessApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    // To Reload
    private val _navigateReload = MutableLiveData<Event<Boolean>>()
    val navigateReload: LiveData<Event<Boolean>>
        get() = _navigateReload

    var processId: Int = 0
        set(value) {
            field = value
        }

    var isUserManager: Boolean = false

    val candidateName: MutableLiveData<String> = MutableLiveData("")
    val candidateAvatar: MutableLiveData<String> = MutableLiveData("")
    val jobTitle: MutableLiveData<String> = MutableLiveData("")

    val interviewerText: MutableLiveData<String> = MutableLiveData("")
    val interviewerListAdapter: ReviewerListAdapter = ReviewerListAdapter()

    val interviewAdvancerText: MutableLiveData<String> = MutableLiveData("")
    val interviewAdvancerListAdapter: ReviewerListAdapter = ReviewerListAdapter()

    val offerManagerText: MutableLiveData<String> = MutableLiveData("")
    val offerManagerListAdapter: ReviewerListAdapter = ReviewerListAdapter()

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadProcessSetting(cashMode: Boolean) {

        val apiObservable: Observable<ProcessSettingEntity>

        if (cashMode) {
            apiObservable =
                Observable.fromCallable { processSettingDao.getMyProcessSettingByProcessId(AppPreferences.myId, processId) }
                    .concatMap { dbProcessSettingData ->
                        if (dbProcessSettingData.isEmpty()) {
                            processApi.getProcessSettings(AppPreferences.apiAccessToken, processId)
                                .concatMap { apiProcessSetting ->
                                    Observable.just(parseJsonResult(apiProcessSetting))
                                }
                        } else {
                            val processSettingEntity = dbProcessSettingData[0]
                            val reviewerList = reviewerDao.getReviewerByParentId(Enums.SettingType.PROCESS.value, processId, AppPreferences.myId)
                            Observable.just(parseEntityResult(processSettingEntity, reviewerList))
                        }
                    }
        } else {
            apiObservable = Observable.fromCallable { }
                .concatMap {
                    processApi.getProcessSettings(AppPreferences.apiAccessToken, processId)
                        .concatMap { apiProcessSetting ->
                            Observable.just(parseJsonResult(apiProcessSetting))
                        }
                }
        }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveProcessSettingStart() }
            .doOnTerminate { onRetrieveProcessSettingFinish() }
            .subscribe(
                { result -> onRetrieveProcessSettingSuccess(result) },
                { error -> onRetrieveProcessSettingError(error) }
            )
    }

    private fun parseJsonResult(json: ProcessSettingJson): ProcessSettingEntity {

        val processSetting: ProcessSettingEntity = json.toProcessSettingEntity(processId, AppPreferences.myId)

        val reviewerList: ArrayList<ReviewerEntity> = arrayListOf()
        reviewerList.addAll(json.toReviewerList(processId, AppPreferences.myId))
        // Update ProcessSetting & Reviewer Db
        processSettingDao.deleteAll()
        reviewerDao.deleteAll(Enums.SettingType.PROCESS.value)

        // Update Process Setting Table
        processSettingDao.insertAll(processSetting)
        reviewerDao.insertAll(*reviewerList.toTypedArray())
        return parseEntityResult(processSetting, reviewerList)
    }

    private fun parseEntityResult(processSetting: ProcessSettingEntity, reviewerList: List<ReviewerEntity>): ProcessSettingEntity {
        val userManagerList: ArrayList<ReviewerEntity> = arrayListOf()
        val interviewerList: ArrayList<ReviewerEntity> = arrayListOf()
        val interviewAdvancerList: ArrayList<ReviewerEntity> = arrayListOf()
        val offerManagerList: ArrayList<ReviewerEntity> = arrayListOf()

        for(reviewer in reviewerList) {
            when (reviewer.reviewerType) {
                Enums.ReviewerType.USER_MANAGER.value -> {
                    userManagerList.add(reviewer)
                }
                Enums.ReviewerType.INTERVIEWER.value -> {
                    interviewerList.add(reviewer)
                }
                Enums.ReviewerType.INTERVIEWER_ADVANCER.value -> {
                    interviewAdvancerList.add(reviewer)
                }
                Enums.ReviewerType.OFFER_MANAGER.value -> {
                    offerManagerList.add(reviewer)
                }
            }
        }

        processSetting.setUserManagerList(userManagerList)
        processSetting.setInterviewerList(interviewerList)
        processSetting.setInterviewAdvancerList(interviewAdvancerList)
        processSetting.setOfferManagerList(offerManagerList)

        return processSetting
    }

    fun removeUserRoleToProcessSetting(processId: Int, role: String, clientId: Int, reviewerEntityId: Int) {

        subscription = processApi.removeUserRoleProcessSetting(AppPreferences.apiAccessToken, processId, role, clientId).concatMap {
                addResult ->
                    Observable.just(addResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveProcessSettingStart() }
            .doOnTerminate { onRetrieveProcessSettingFinish() }
            .subscribe(
                { result -> onRemoveRoleSuccess() },
                { error -> onRetrieveProcessSettingError(error) }
            )
    }

    private fun onRetrieveProcessSettingStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveProcessSettingFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveProcessSettingSuccess(processSetting: ProcessSettingEntity) {

        isUserManager = processSetting.isUserManager

        interviewerListAdapter.updateReviewerList(processSetting.getInterviewerList(), processId, true)
        interviewAdvancerListAdapter.updateReviewerList(processSetting.getInterviewAdvancerList(), processId, true)
        offerManagerListAdapter.updateReviewerList(processSetting.getOfferManagerList(), processId, true)

        jobTitle.value = String.format(context.resources.getString(R.string.job_and_location), processSetting.jobTitle, processSetting.cityName)
        candidateName.value = processSetting.candidateFullName
        candidateAvatar.value = processSetting.candidateAvatar

        interviewerText.value = context.resources.getQuantityString(
            R.plurals.interviewer,
            processSetting.getInterviewerList().size,
            processSetting.getInterviewerList().size
        )

        interviewAdvancerText.value = context.resources.getQuantityString(
            R.plurals.interviewer_advancer,
            processSetting.getInterviewAdvancerList().size,
            processSetting.getInterviewAdvancerList().size
        )

        offerManagerText.value = context.resources.getQuantityString(
            R.plurals.offer_manager,
            processSetting.getOfferManagerList().size,
            processSetting.getOfferManagerList().size
        )
    }

    private fun onRemoveRoleSuccess() {
        _navigateReload.value = Event(true)
    }

    private fun onRetrieveProcessSettingError(e: Throwable) {
        e.printStackTrace()
    }
}