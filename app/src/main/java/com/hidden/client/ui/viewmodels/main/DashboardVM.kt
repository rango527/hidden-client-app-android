package com.hidden.client.ui.viewmodels.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hidden.client.apis.DashboardApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.models.entity.DashboardTileContentEntity
import com.hidden.client.models.entity.DashboardTileEntity
import com.hidden.client.models.dao.DashboardTileContentDao
import com.hidden.client.models.dao.DashboardTileDao
import com.hidden.client.models.json.DashboardTileJson
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DashboardVM (
    private val dashboardTileDao: DashboardTileDao,
    private val dashboardTileContentDao: DashboardTileContentDao
) : RootVM() {

    @Inject
    lateinit var dashboardApi: DashboardApi

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val dashboardTileList: MutableLiveData<List<DashboardTileEntity>> = MutableLiveData()

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
                            Log.d("testing", "testmyIds1")
                            dashboardApi.getDashboard(AppPreferences.apiAccessToken)
                                .concatMap { apiDashboardTileList ->
                                    Log.d("testing", "testmyIds11")
                                    Observable.just(parseJsonResult(apiDashboardTileList))
                                }
                        } else {
                            Log.d("testing", "testmyIds2")
                            Observable.just(parseEntityResult(dbTileListData))
                        }
                    }
        } else {
            apiObservable = Observable.fromCallable { }
                .concatMap {
                    Log.d("testing", "testmyIds3")
                    dashboardApi.getDashboard(AppPreferences.apiAccessToken)
                        .concatMap { apiDashboardTileList ->
                            Log.d("testing", "testmyIds4")
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
}