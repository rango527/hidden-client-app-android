package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.ShortlistApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.dao.*
import com.hidden.client.models.entity.*
import com.hidden.client.models.json.*
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import javax.inject.Inject

class ShortlistListVM(
    private val context: Context,
    private val shortlistDao: ShortlistDao,
    private val shortlistCandidateDao: ShortlistCandidateDao,
    private val brandDao: BrandDao,
    private val projectDao: ProjectDao,
    private val projectAssetsDao: ProjectAssetsDao,
    private val workExperienceDao: WorkExperienceDao,
    private val skillDao: SkillDao
) : RootVM() {

    @Inject
    lateinit var shortlistApi: ShortlistApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val successUpdate: MutableLiveData<Boolean> = MutableLiveData()

    val shortlist: MutableLiveData<ShortlistEntity> = MutableLiveData()
    val candidateList: MutableLiveData<List<ShortlistViewVM>> = MutableLiveData()
    val consentList: MutableLiveData<List<ConsentEntity>> = MutableLiveData()
    val consentTerms: MutableLiveData<ConsentTermsAndPrivacyEntity> = MutableLiveData()
    val consentPrivacy: MutableLiveData<ConsentTermsAndPrivacyEntity> = MutableLiveData()

    private var subscription: Disposable? = null

    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean>
        get() = _isFormValid

    var isChecked: Boolean = false
        set(value) {
            field = value
            validateForm()
        }

    private fun validateForm() {
        if (isChecked) {
            _isFormValid.postValue(true)
        } else {
            _isFormValid.postValue(false)
        }
    }
    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun getConsentUpdate() {
        val apiObservable: Observable<List<ConsentEntity>>
        apiObservable = Observable.fromCallable { }.concatMap { shortlistApi.getConsentUpdate(
            AppPreferences.apiAccessToken
        ).concatMap { apiResult ->
            Observable.just(parseConsentJsonResult(apiResult))
        } }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveShortlistStart() }
            .doOnTerminate { onRetrieveShortlistFinish() }
            .subscribe(
                { result -> onRetrieveConsentSuccess(result) },
                { error -> onRetrieveConsentError(error) }
            )
    }

    fun updateConsent(body: RequestBody) {
        subscription = shortlistApi.updateConsent(
            "application/json",
            AppPreferences.apiAccessToken,
            body
        ).concatMap { addResult ->
            Observable.just(addResult)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onSubmitSuccess(result) },
                { error -> onSubmitError(error) }
            )
    }

    fun getConsentTerms() {
        val apiObservable: Observable<ConsentTermsAndPrivacyEntity>
        apiObservable = Observable.fromCallable { }.concatMap { shortlistApi.getConsentTerms(
            AppPreferences.apiAccessToken
        ).concatMap { apiResult ->
            Observable.just(parseConsentTPJsonResult(apiResult))
        } }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveShortlistStart() }
            .doOnTerminate { onRetrieveShortlistFinish() }
            .subscribe(
                { result -> onRetrieveConsentTermsSuccess(result) },
                { error -> onRetrieveConsentTermsError(error) }
            )
    }

    fun getConsentPrivacy() {
        val apiObservable: Observable<ConsentTermsAndPrivacyEntity>
        apiObservable = Observable.fromCallable { }.concatMap { shortlistApi.getConsentPrivacy(
            AppPreferences.apiAccessToken
        ).concatMap { apiResult ->
            Observable.just(parseConsentTPJsonResult(apiResult))
        } }

        subscription = apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveShortlistStart() }
            .doOnTerminate { onRetrieveShortlistFinish() }
            .subscribe(
                { result -> onRetrieveConsentPrivacySuccess(result) },
                { error -> onRetrieveConsentPrivacyError(error) }
            )
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

    private fun parseConsentJsonResult(jsonList: List<ConsentJson>): List<ConsentEntity> {
        val consentList: ArrayList<ConsentEntity> = arrayListOf()
        for (json in jsonList) {
            val consent: ConsentEntity = json.toEntity(AppPreferences.myId)
            consentList.add(consent)
        }
        return consentList
    }

    private fun parseConsentTPJsonResult(json: ConsentTermsAndPrivacyJson): ConsentTermsAndPrivacyEntity {
        return json.toEntity()
    }

    private fun parseJsonResult(json: ShortlistJson): ShortlistEntity {
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
//            val feedbackList: List<FeedbackEntity> = candidateJson.toFeedbackEntityList(candidateJson.id.safeValue())
//            feedbackDao.insertAll(*feedbackList.toTypedArray())

            // Update FeedbackQuestion Table
//            for (feedback in candidateJson.feedbackList!!) {
//                feedbackQuestionDao.insertAll(*feedback.toQuestionList(feedback.id.safeValue(), false).toTypedArray())
//            }
        }

        // Update ShortlistCandidate Table
        val candidateList: List<ShortlistCandidateEntity> =
            json.toShortlistCandidateEntityList(json.clientId.safeValue())

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

        val shortlist: ShortlistEntity = json.toEntity()

        // Update Shortlist Table
        shortlistDao.deleteAll()
        shortlistDao.insertAll(shortlist)

        shortlistCandidateDao.deleteAll()
        shortlistCandidateDao.insertAll(
            *candidateList.toTypedArray()
        )

        shortlist.setCandidateList(candidateList)


        return shortlist
    }

    private fun parseEntityResult(shortlist: ShortlistEntity): ShortlistEntity {
        val candidateList = shortlistCandidateDao.getCandidateByClientId(shortlist.clientId.safeValue())

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
        HCGlobal.getInstance().currentIndex = 0

        val shortlistViewVMList: ArrayList<ShortlistViewVM> = arrayListOf()
        for (candidate in shortlist.getCandidateList()) {
            shortlistViewVMList.add(ShortlistViewVM(candidate))
        }
        this.candidateList.value = shortlistViewVMList
    }

    private fun onRetrieveShortlistError(e: Throwable) {
        e.printStackTrace()
    }

    private fun onRetrieveConsentSuccess(consentEntity: List<ConsentEntity>) {
        this.consentList.value = consentEntity
    }

    private fun onRetrieveConsentError(e: Throwable) {
        e.printStackTrace()
    }

    private fun onRetrieveConsentTermsSuccess(json: ConsentTermsAndPrivacyEntity) {
        this.consentTerms.value = json
    }

    private fun onRetrieveConsentTermsError(e: Throwable) {
        e.printStackTrace()
    }

    private fun onRetrieveConsentPrivacySuccess(json: ConsentTermsAndPrivacyEntity) {
        this.consentPrivacy.value = json
    }

    private fun onRetrieveConsentPrivacyError(e: Throwable) {
        e.printStackTrace()
    }

    private fun onSubmitSuccess(simpleResponseJson: SimpleResponseJson) {
        this.successUpdate.value = true
    }

    private fun onSubmitError(e: Throwable) {
        HToast.show(HCGlobal.getInstance().currentActivity, "Sorry, can't accept", HToast.TOAST_ERROR)
        e.printStackTrace()
    }
}