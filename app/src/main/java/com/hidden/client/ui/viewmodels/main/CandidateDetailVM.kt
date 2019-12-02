package com.hidden.client.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.CandidateApi
import com.hidden.client.models.json.CandidateJson
import com.hidden.client.models.dao.*
import com.hidden.client.models.entity.CandidateEntity
import com.hidden.client.ui.adapters.CandidateListAdapter
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CandidateDetailVM(private val context: Context,
                        private val candidateDao: CandidateDao,
                        private val brandDao: BrandDao,
                        private val projectDao: ProjectDao,
                        private val skillDao: SkillDao,
                        private val workExperienceDao: WorkExperienceDao): RootVM() {

    var candidateId = 0
        set(value) {
            field = value
        }

    @Inject
    lateinit var candidateApi: CandidateApi
    val candidateListAdapter: CandidateListAdapter = CandidateListAdapter()

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()

    val candidateInfo: MutableLiveData<CandidateJson> = MutableLiveData()

    private lateinit var subscription: Disposable

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadCandidateList(getOnlyFromLocal: Boolean){
//        subscription = Observable.fromCallable { candidateDao.getCandidateById(candidateId) }
//            .concatMap {
//                    dbCandidateList ->
//                if(dbCandidateList.isEmpty() && !getOnlyFromLocal)
//                    candidateApi.getCandidateById(AppPreferences.apiAccessToken, candidateId.toString()).concatMap {
//                        apiCandidateList -> candidateDao.insertAll(*apiCandidateList.toTypedArray())
//                        Observable.just(apiCandidateList)
//                    }
//                else
//                    Observable.just(dbCandidateList)
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { onRetrieveCandidateListStart() }
//            .doOnTerminate { onRetrieveCandidateListFinish() }
//            .subscribe(
//                { result -> onRetrieveCandidateListSuccess(result) },
//                { error -> onRetrieveCandidateListError(error) }
//            )
    }

    private fun onRetrieveCandidateListStart(){
        loadingVisibility.value = true
        errorMessage.value = null
    }

    private fun onRetrieveCandidateListFinish(){
        loadingVisibility.value = false
    }

    private fun onRetrieveCandidateListSuccess(candidateList: List<CandidateEntity>){
        candidateListAdapter.updateCandidateList(candidateList)
    }

    private fun onRetrieveCandidateListError(e: Throwable){
        e.printStackTrace()
        errorMessage.value = R.string.server_error
    }
}