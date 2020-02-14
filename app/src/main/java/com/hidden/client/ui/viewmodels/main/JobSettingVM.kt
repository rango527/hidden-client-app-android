package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.JobApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.dao.JobSettingDao
import com.hidden.client.models.dao.ReviewerDao
import com.hidden.client.models.entity.JobSettingEntity
import com.hidden.client.models.entity.ReviewerEntity
import com.hidden.client.models.json.JobSettingJson
import com.hidden.client.ui.adapters.ReviewerListAdapter
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class JobSettingVM(
    private val context: Context,
    private val jobSettingDao: JobSettingDao,
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

        val apiObservable: Observable<JobSettingEntity>

        if (cashMode) {
            apiObservable =
                Observable.fromCallable { jobSettingDao.getMyJobSettingByJobId(AppPreferences.myId, jobId) }
                    .concatMap { dbJobSettingData ->
                        if (dbJobSettingData.isEmpty()) {
                            jobApi.getJobSettings(AppPreferences.apiAccessToken, jobId)
                                .concatMap { apiJobSetting ->
                                    Observable.just(parseJsonResult(apiJobSetting))
                                }
                        } else {
                            val jobSettingEntity = dbJobSettingData[0]
                            val reviewerList = reviewerDao.getReviewerByJobId(AppPreferences.myId, jobId)
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

    private fun parseJsonResult(json: JobSettingJson): JobSettingEntity {

        val jobSetting: JobSettingEntity = json.toJobSettingEntity(jobId, AppPreferences.myId)

        val reviewerList: ArrayList<ReviewerEntity> = arrayListOf()
        reviewerList.addAll(json.toReviewerList(jobId, AppPreferences.myId))

        // Update JobSetting & Reviewer Db
        jobSettingDao.deleteAll()
        reviewerDao.deleteAll()

        // Update Job Setting Table
        jobSettingDao.insertAll(jobSetting)
        reviewerDao.insertAll(*reviewerList.toTypedArray())

        return parseEntityResult(jobSetting, reviewerList)
    }

    private fun parseEntityResult(jobSetting: JobSettingEntity, reviewerList: List<ReviewerEntity>): JobSettingEntity {
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

        jobSetting.setUserManagerList(userManagerList)
        jobSetting.setShortlistReviewerList(shortlistReviewerList)
        jobSetting.setInterviewerList(interviewerList)
        jobSetting.setInterviewAdvancerList(interviewAdvancerList)
        jobSetting.setOfferManagerList(offerManagerList)

        return jobSetting
    }

    private fun onRetrieveJobSettingStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveJobSettingFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveJobSettingSuccess(jobSetting: JobSettingEntity) {

        shortlistReviewerListAdapter.updateReviewerList(jobSetting.getShortlistReviewerList())
        interviewerListAdapter.updateReviewerList(jobSetting.getInterviewerList())
        interviewAdvancerListAdapter.updateReviewerList(jobSetting.getInterviewAdvancerList())
        offerManagerListAdapter.updateReviewerList(jobSetting.getOfferManagerList())

        jobTitle.value = jobSetting.jobTitle
        reviewText.value = jobSetting.reviewType

        shortlistReviewerText.value = context.resources.getQuantityString(
                R.plurals.shortlist_reviewer,
                jobSetting.getShortlistReviewerList().size,
                jobSetting.getShortlistReviewerList().size
            )

        interviewerText.value = context.resources.getQuantityString(
            R.plurals.interviewer,
            jobSetting.getShortlistReviewerList().size,
            jobSetting.getShortlistReviewerList().size
        )

        interviewAdvancerText.value = context.resources.getQuantityString(
            R.plurals.interviewer_advancer,
            jobSetting.getShortlistReviewerList().size,
            jobSetting.getShortlistReviewerList().size
        )

        offerManagerText.value = context.resources.getQuantityString(
            R.plurals.offer_manager,
            jobSetting.getShortlistReviewerList().size,
            jobSetting.getShortlistReviewerList().size
        )
    }

    private fun onRetrieveJobSettingError(e: Throwable) {
        e.printStackTrace()
    }
}