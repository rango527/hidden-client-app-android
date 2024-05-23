package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.DashboardApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.models.dao.CandidateDao
import com.hidden.client.models.entity.DashboardTileContentEntity
import com.hidden.client.models.entity.DashboardTileEntity
import com.hidden.client.models.dao.*
import com.hidden.client.models.json.DashboardTileJson
import com.hidden.client.models.json.SimpleResponseJson
import com.hidden.client.ui.viewmodels.event.Event
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DashboardVM (
    private var brandDao: BrandDao,
    private var candidateDao: CandidateDao,
    private var conversationDao: ConversationDao,
    private var dashboardTileContentDao: DashboardTileContentDao,
    private var dashboardTileDao: DashboardTileDao,
    private var feedbackDao: FeedbackDao,
    private var feedbackIDDao: FeedbackIDDao,
    private var feedbackQuestionDao: FeedbackQuestionDao,
    private var interviewParticipantDao: InterviewParticipantDao,
    private var jobSettingDao: JobSettingDao,
    private var messageListDao: MessageListDao,
    private var processDao: ProcessDao,
    private var processSettingDao: ProcessSettingDao,
    private var projectAssetsDao: ProjectAssetsDao,
    private var processStageDao: ProcessStageDao,
    private var projectDao: ProjectDao,
    private var reviewerDao: ReviewerDao,
    private var shortlistCandidateDao: ShortlistCandidateDao,
    private var shortlistDao: ShortlistDao,
    private var skillDao: SkillDao,
    private var timelineDao: TimelineDao,
    private var workExperienceDao: WorkExperienceDao
) : RootVM() {

    @Inject
    lateinit var dashboardApi: DashboardApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val dashboardTileList: MutableLiveData<List<DashboardTileEntity>> = MutableLiveData()
    val logOutJson: MutableLiveData<SimpleResponseJson> = MutableLiveData()

    private val _navigateHome = MutableLiveData<Event<Boolean>>()
    val navigateHome: LiveData<Event<Boolean>>
        get() = _navigateHome

    private var subscription: Disposable? = null

    init {
        loadDashboard(true)
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadDashboard(cashMode: Boolean) {

        val apiObservable: Observable<List<DashboardTileEntity>>

        if (cashMode) {
            apiObservable =
                Observable.fromCallable { dashboardTileDao.getTileList(AppPreferences.myId) }
                    .concatMap { dbTileListData ->
                        if (dbTileListData.isEmpty()) {
                            dashboardApi.getDashboard(AppPreferences.apiAccessToken)
                                .concatMap { apiDashboardTileList ->
                                    Observable.just(parseJsonResult(apiDashboardTileList))
                                }
                        } else {
                            Observable.just(parseEntityResult(dbTileListData))
                        }
                    }
        } else {
            apiObservable = Observable.fromCallable { }
                .concatMap {
                    dashboardApi.getDashboard(AppPreferences.apiAccessToken)
                        .concatMap { apiDashboardTileList ->
                            Observable.just(parseJsonResult(apiDashboardTileList))
                        }
                }
        }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveDashboardStart() }
            .doOnTerminate { onRetrieveDashboardFinish() }
            .subscribe(
                { result -> onRetrieveDashboardSuccess(result) },
                { error -> onRetrieveDashboardError(error) }
            )
    }

    fun logOut() {
        val logOutJson: Observable<SimpleResponseJson>

        logOutJson = dashboardApi.clientLogout(AppPreferences.apiAccessToken).concatMap {
                apiSimpleJson -> Observable.just(logOutResult(apiSimpleJson))
        }

        subscription = logOutJson.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveDashboardStart() }
            .doOnTerminate { onRetrieveDashboardFinish() }
            .subscribe(
                { result -> onLogOutSuccess(result) },
                { error -> onLogOutError(error) }
            )
    }

    private fun logOutResult(json: SimpleResponseJson): SimpleResponseJson {
        // clear all cache and local data
        brandDao.deleteAll()
        candidateDao.deleteAll()
        conversationDao.deleteAll()
        dashboardTileContentDao.deleteAll()
        dashboardTileDao.deleteAll()
        feedbackDao.deleteAll()
        feedbackIDDao.deleteAll()
        feedbackQuestionDao.deleteAll()
        interviewParticipantDao.deleteAll()
        jobSettingDao.deleteAll()
        messageListDao.deleteAll()
        processDao.deleteAll()
        processSettingDao.deleteAll()
        processStageDao.deleteAll()
        projectAssetsDao.deleteAll()
        projectDao.deleteAll()
        reviewerDao.deleteAll(Enums.SettingType.JOB.value)
        shortlistCandidateDao.deleteAll()
        shortlistDao.deleteAll()
        skillDao.deleteAll()
        timelineDao.deleteAll()
        workExperienceDao.deleteAll()
        return json
    }

    private fun parseJsonResult(json: List<DashboardTileJson>): List<DashboardTileEntity> {

        val tileEntityList: ArrayList<DashboardTileEntity> = arrayListOf()

        dashboardTileDao.deleteAll()
        dashboardTileContentDao.deleteAll()

        for (tileJson in json) {

            val tileEntity: DashboardTileEntity = tileJson.toEntity()

            val pDashboardTileId = dashboardTileDao.insert(tileEntity).toInt()
            tileEntity.id = pDashboardTileId

            if (tileJson.tileContentList!!.isNotEmpty()) {
                tileEntity.setTileContentList(tileJson.toTileContentList(pDashboardTileId))
            }

            dashboardTileContentDao.insertAll(*tileEntity.getTileContentList().toTypedArray())

            tileEntityList.add(tileEntity)
        }

        return tileEntityList
    }

    private fun parseEntityResult(tileEntityList: List<DashboardTileEntity>): List<DashboardTileEntity> {

        for (tileEntity in tileEntityList) {
            val tileContentEntityList: List<DashboardTileContentEntity> = dashboardTileContentDao.getContentByTileId(tileEntity.id)
            tileEntity.setTileContentList(tileContentEntityList)
        }

        return tileEntityList
    }

    private fun onRetrieveDashboardStart(){
        loadingVisibility.value = true
    }

    private fun onRetrieveDashboardFinish(){
        loadingVisibility.value = false
    }

    private fun onRetrieveDashboardSuccess(tileEntityList: List<DashboardTileEntity>){

        this.dashboardTileList.value = tileEntityList
    }

    private fun onRetrieveDashboardError(e: Throwable){
        e.printStackTrace()
    }

    private fun onLogOutSuccess(simpleResponseJson: SimpleResponseJson) {
        _navigateHome.value = Event(true)
    }

    private fun onLogOutError(e: Throwable) {
        e.printStackTrace()
    }
}