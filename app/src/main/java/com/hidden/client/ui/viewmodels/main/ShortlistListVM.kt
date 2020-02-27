package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ShortlistApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.dao.*
import com.hidden.client.models.entity.*
import com.hidden.client.models.json.ShortlistCandidateJson
import com.hidden.client.models.json.ShortlistJson
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShortlistListVM(
    private val shortlistDao: ShortlistDao,
    private val shortlistCandidateDao: ShortlistCandidateDao,
    private val feedbackDao: FeedbackDao,
    private val feedbackQuestionDao: FeedbackQuestionDao,
    private val brandDao: BrandDao,
    private val projectDao: ProjectDao,
    private val projectAssetsDao: ProjectAssetsDao,
    private val workExperienceDao: WorkExperienceDao,
    private val skillDao: SkillDao
) : RootVM() {

    @Inject
    lateinit var shortlistApi: ShortlistApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val shortlist: MutableLiveData<ShortlistEntity> = MutableLiveData()
    val candidateList: MutableLiveData<List<ShortlistViewVM>> = MutableLiveData()

    private var subscription: Disposable? = null

    init {
        loadShortlistList(true)
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadShortlistList(cashMode: Boolean) {

        val apiObservable: Observable<ShortlistEntity>

        if (cashMode) {
            apiObservable =
                Observable.fromCallable { shortlistDao.getShortlistById(AppPreferences.myId) }
                    .concatMap { dbShortlistData ->
                        if (dbShortlistData.isEmpty()) {
                            shortlistApi.getShortlist(AppPreferences.apiAccessToken)
                                .concatMap { apiShortlist ->
                                    Observable.just(parseJsonResult(apiShortlist))
                                }
                        } else {
                            Observable.just(parseEntityResult(dbShortlistData[0]))
                        }
                    }
        } else {
            apiObservable = Observable.fromCallable { }
                .concatMap {
                    shortlistApi.getShortlist(AppPreferences.apiAccessToken)
                        .concatMap { apiShortlist ->
                            Observable.just(parseJsonResult(apiShortlist))
                        }
                }
        }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveShortlistStart() }
            .doOnTerminate { onRetrieveShortlistFinish() }
            .subscribe(
                { result -> onRetrieveShortlistSuccess(result) },
                { error -> onRetrieveShortlistError(error) }
            )
    }

    private fun parseJsonResult(json: ShortlistJson): ShortlistEntity {

        val shortlist: ShortlistEntity = json.toEntity()

        // Update Shortlist Table
        shortlistDao.deleteAll()
        shortlistDao.insertAll(shortlist)

        // Update ShortlistCandidate Table
        val candidateList: List<ShortlistCandidateEntity> =
            json.toShortlistCandidateEntityList(json.clientId.safeValue())

        shortlistCandidateDao.deleteAll()
        shortlistCandidateDao.insertAll(
            *candidateList.toTypedArray()
        )

        val candidateJsonList: List<ShortlistCandidateJson> = json.shortlistCandidates!!
        for (candidateJson in candidateJsonList) {

            // Update Brand Table
            val brandList: List<BrandEntity> =
                candidateJson.toBrandEntityList(candidateJson.id.safeValue())
            brandDao.insertAll(*brandList.toTypedArray())

            // Update Project Table
            val projectList: List<ProjectEntity> =
                candidateJson.toProjectEntityList(candidateJson.id.safeValue())
            projectDao.insertAll(*projectList.toTypedArray())

            // Update ProjectAssets Table
            for (project in projectList) {
                projectAssetsDao.insertAll(*project.getAssetsList().toTypedArray())
            }

            // Update WorkExperience  Table
            val experienceList: List<WorkExperienceEntity> =
                candidateJson.toExperienceEntityList(candidateJson.id.safeValue())
            workExperienceDao.insertAll(*experienceList.toTypedArray())

            // Update Skill Table
            val skillList: List<SkillEntity> =
                candidateJson.toSkillEntityList(candidateJson.id.safeValue())
            skillDao.insertAll(*skillList.toTypedArray())

            // Update Feedback Table
            val feedbackList: List<FeedbackEntity> = candidateJson.toFeedbackEntityList(candidateJson.id.safeValue())
            feedbackDao.insertAll(*feedbackList.toTypedArray())

            // Update FeedbackQuestion Table
            for (feedback in candidateJson.feedbackList!!) {
                feedbackQuestionDao.insertAll(*feedback.toQuestionList(feedback.id.safeValue()).toTypedArray())
            }

        }

        candidateList.forEachIndexed { index, element ->

            candidateList[index].setBrandList(
                json.shortlistCandidates[index].toBrandEntityList(element.candidateId)
            )
            candidateList[index].setProjectList(
                json.shortlistCandidates[index].toProjectEntityList(element.candidateId)
            )
            candidateList[index].setExperienceList(
                json.shortlistCandidates[index].toExperienceEntityList(element.candidateId)
            )
            candidateList[index].setSkillList(
                json.shortlistCandidates[index].toSkillEntityList(element.candidateId)
            )
        }

        shortlist.setCandidateList(candidateList)

        return shortlist
    }

    private fun parseEntityResult(shortlist: ShortlistEntity): ShortlistEntity {
        val candidateList = shortlistCandidateDao.getCandidateByClientId(shortlist.clientId)

        candidateList.forEachIndexed { index, element ->
            candidateList[index].setBrandList(
                brandDao.getBrandByCandidateId(
                    element.candidateId
                )
            )

            val projectList = projectDao.getProjectByCandidateId(element.candidateId)
            for (project in projectList) {
                project.setAssetsList(projectAssetsDao.getProjectAssetsByProjectId(project.id))
            }
            candidateList[index].setProjectList(projectList)

            candidateList[index].setExperienceList(
                workExperienceDao.getExperienceByCandidateId(
                    element.candidateId
                )
            )
            candidateList[index].setSkillList(
                skillDao.getSkillByCandidateId(
                    element.candidateId
                )
            )
        }

        shortlist.setCandidateList(candidateList)

        return shortlist
    }

    private fun onRetrieveShortlistStart() {
        loadingVisibility.value = true
    }

    private fun onRetrieveShortlistFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrieveShortlistSuccess(shortlist: ShortlistEntity) {
        this.shortlist.value = shortlist

        val shortlistViewVMList: ArrayList<ShortlistViewVM> = arrayListOf()
        for (candidate in shortlist.getCandidateList()) {
            shortlistViewVMList.add(ShortlistViewVM(candidate))
        }
        this.candidateList.value = shortlistViewVMList
    }

    private fun onRetrieveShortlistError(e: Throwable) {
        e.printStackTrace()
    }
}