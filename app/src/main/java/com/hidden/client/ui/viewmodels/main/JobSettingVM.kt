package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.JobApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.models.dao.SettingDao
import com.hidden.client.models.dao.ReviewerDao
import com.hidden.client.models.entity.SettingEntity
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.models.json.JobSettingJson
import com.hidden.client.ui.adapters.ReviewerListAdapter
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class JobSettingVM(
    private val context: Context,
    private val settingDao: SettingDao,
    private val reviewerDao: ReviewerDao
) : RootVM() {

    @Inject
    lateinit var jobApi: JobApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    var jobId: Int = 0
        set(value) {
            field = value
            loadJobSetting(true)
        }

    var isUserManager: Boolean = false

    val jobTitle: MutableLiveData<String> = MutableLiveData("")
    val reviewText: MutableLiveData<String> = MutableLiveData("")

    val shortlistReviewerText: MutableLiveData<String> = MutableLiveData("")
    val shortlistReviewerListAdapter: ReviewerListAdapter = ReviewerListAdapter()

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

    fun loadJobSetting(cashMode: Boolean) {

        val apiObservable: Observable<SettingEntity>

        if (cashMode) {
            apiObservable =
                Observable.fromCallable { settingDao.getMyJobSettingByJobId(AppPreferences.myId, jobId) }
                    .concatMap { dbJobSettingData ->
                        if (dbJobSettingData.isEmpty()) {
                            jobApi.getJobSettings(AppPreferences.apiAccessToken, jobId)
                                .concatMap { apiJobSetting ->
                                    Observable.just(parseJsonResult(apiJobSetting))
                                }
                        } else {
                            val jobSettingEntity = dbJobSettingData[0]
                            val reviewerList = reviewerDao.getReviewerByParentId(Enums.SettingType.JOB.value, jobId, AppPreferences.myId)
                            Observable.just(parseEntityResult(jobSettingEntity, reviewerList))
                        }
                    }
        } else {
            apiObservable = Observable.fromCallable { }
                .concatMap {
                    jobApi.getJobSettings(AppPreferences.apiAccessToken, jobId)
                        .concatMap { apiJobSetting ->
                            Observable.just(parseJsonResult(apiJobSetting))
                        }
                }
        }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveJobSettingStart() }
            .doOnTerminate { onRetrieveJobSettingFinish() }
            .subscribe(
                { result -> onRetrieveJobSettingSuccess(result) },
                { error -> onRetrieveJobSettingError(error) }
            )
    }

    private fun parseJsonResult(json: JobSettingJson): SettingEntity {

        val setting: SettingEntity = json.toJobSettingEntity(jobId, AppPreferences.myId)

        val reviewerList: ArrayList<ReviewerEntity> = arrayListOf()
        reviewerList.addAll(json.toReviewerList(jobId, AppPreferences.myId))

        // Update JobSetting & Reviewer Db
        settingDao.deleteAll()
        reviewerDao.deleteAll(Enums.SettingType.JOB.value)

        // Update Job Setting Table
        settingDao.insertAll(setting)
        reviewerDao.insertAll(*reviewerList.toTypedArray())

        return parseEntityResult(setting, reviewerList)
    }

    private fun parseEntityResult(setting: SettingEntity, reviewerList: List<ReviewerEntity>): SettingEntity {
        val userManagerList: ArrayList<ReviewerEntity> = arrayListOf()
        val shortlistReviewerList: ArrayList<ReviewerEntity> = arrayListOf()
        val interviewerList: ArrayList<ReviewerEntity> = arrayListOf()
        val interviewAdvancerList: ArrayList<ReviewerEntity> = arrayListOf()
        val offerManagerList: ArrayList<ReviewerEntity> = arrayListOf()

        for(reviewer in reviewerList) {
            when (reviewer.reviewerType) {
                Enums.ReviewerType.USER_MANAGER.value -> {
                    userManagerList.add(reviewer)
                }
                Enums.ReviewerType.SHORTLIST_REVIEWER.value -> {
                    shortlistReviewerList.add(reviewer)
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

        setting.setUserManagerList(userManagerList)
        setting.setShortlistReviewerList(shortlistReviewerList)
        setting.setInterviewerList(interviewerList)
        setting.setInterviewAdvancerList(interviewAdvancerList)
        setting.setOfferManagerList(offerManagerList)

        return setting
    }

    private fun onRetrieveJobSettingStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveJobSettingFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveJobSettingSuccess(setting: SettingEntity) {

        isUserManager = setting.isUserManager

        shortlistReviewerListAdapter.updateReviewerList(setting.getShortlistReviewerList())
        interviewerListAdapter.updateReviewerList(setting.getInterviewerList())
        interviewAdvancerListAdapter.updateReviewerList(setting.getInterviewAdvancerList())
        offerManagerListAdapter.updateReviewerList(setting.getOfferManagerList())

        jobTitle.value = setting.jobTitle
        reviewText.value = setting.reviewType

        shortlistReviewerText.value = context.resources.getQuantityString(
                R.plurals.shortlist_reviewer,
                setting.getShortlistReviewerList().size,
                setting.getShortlistReviewerList().size
            )

        interviewerText.value = context.resources.getQuantityString(
            R.plurals.interviewer,
            setting.getShortlistReviewerList().size,
            setting.getShortlistReviewerList().size
        )

        interviewAdvancerText.value = context.resources.getQuantityString(
            R.plurals.interviewer_advancer,
            setting.getShortlistReviewerList().size,
            setting.getShortlistReviewerList().size
        )

        offerManagerText.value = context.resources.getQuantityString(
            R.plurals.offer_manager,
            setting.getShortlistReviewerList().size,
            setting.getShortlistReviewerList().size
        )
    }

    private fun onRetrieveJobSettingError(e: Throwable) {
        e.printStackTrace()
    }
}