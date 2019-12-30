package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.R
import com.hidden.client.apis.DashboardApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.Enums
import com.hidden.client.models.DashboardTileContentEntity
import com.hidden.client.models.DashboardTileEntity
import com.hidden.client.models.dao.DashboardTileContentDao
import com.hidden.client.ui.viewmodels.custom.DashboardNumberTileViewVM
import com.hidden.client.ui.viewmodels.custom.DashboardPhotoTileViewVM
import com.hidden.client.ui.viewmodels.root.RootVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DashboardTileListVM(private val dashboardTileContentDao: DashboardTileContentDao): RootVM() {

    @Inject
    lateinit var dashboardApi: DashboardApi

    private var yourMetricsList: MutableLiveData<ArrayList<DashboardNumberTileViewVM>> = MutableLiveData()
    private var companyMetricsList: MutableLiveData<ArrayList<DashboardNumberTileViewVM>> = MutableLiveData()
    private var yourJobList: MutableLiveData<ArrayList<DashboardPhotoTileViewVM>> = MutableLiveData()
    private var colleagueJobList: MutableLiveData<ArrayList<DashboardPhotoTileViewVM>> = MutableLiveData()

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    private var subscription: Disposable? = null

    init {
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun loadDashboardTileContent(tileEntity: DashboardTileEntity){
        if (tileEntity.getTileContentList().isNotEmpty()) {
            onRetrieveTileContentListSuccess(tileEntity.getTileContentList(), tileEntity)
        } else {
            if (tileEntity.url != "") {
                subscription = dashboardApi.getTileContent(tileEntity.url, AppPreferences.apiAccessToken).concatMap { apiTileContentList ->

                    val tileContentEntityList: ArrayList<DashboardTileContentEntity> = arrayListOf()
                    for (tileContentJson in apiTileContentList) {
                        val tileContentEntity = tileContentJson.toEntity(tileEntity.id)
                        tileContentEntityList.add(tileContentEntity)
                    }

                    dashboardTileContentDao.deleteByTileId(tileEntity.id)
                    dashboardTileContentDao.insertAll(*tileContentEntityList.toTypedArray())

                    Observable.just(tileContentEntityList)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveTileContentListStart() }
                .doOnTerminate { onRetrieveTileContentListFinish() }
                .subscribe(
                    { result -> onRetrieveTileContentListSuccess(result, tileEntity) },
                    { error -> onRetrieveTileContentListError(error) }
                )
            }
        }
    }

    fun getNumberTileListData(type: String): MutableLiveData<ArrayList<DashboardNumberTileViewVM>> {
        return when(type) {
            Enums.TileTitle.YOUR_METRICS.value -> {
                yourMetricsList
            }
            Enums.TileTitle.COMPANY_METRICS.value -> {
                companyMetricsList
            }
            else -> {
                yourMetricsList
            }
        }
    }

    fun getPhotoTileListData(type: String): MutableLiveData<ArrayList<DashboardPhotoTileViewVM>> {
        return when(type) {
            Enums.TileTitle.YOUR_JOBS.value -> {
                yourJobList
            }
            Enums.TileTitle.COLLEAGUE_JOBS.value -> {
                colleagueJobList
            }
            else -> {
                yourJobList
            }
        }
    }

    private fun onRetrieveTileContentListStart(){
        loadingVisibility.value = true
        errorMessage.value = null
    }

    private fun onRetrieveTileContentListFinish(){
        loadingVisibility.value = false
    }

    private fun onRetrieveTileContentListSuccess(tileContentEntityList: List<DashboardTileContentEntity>, tileEntity: DashboardTileEntity){

        if (tileEntity.type == Enums.TileType.NUMBER_TILE_LIST.value) {
            val viewModelList: ArrayList<DashboardNumberTileViewVM> = arrayListOf()
            for (tileContentEntity in tileContentEntityList) {
                viewModelList.add(
                    DashboardNumberTileViewVM(
                        tileContentEntity,
                        tileEntity.colorScheme
                    )
                )
            }

            if (tileEntity.title == Enums.TileTitle.YOUR_METRICS.value) {
                yourMetricsList.value = viewModelList
            }

            if (tileEntity.title == Enums.TileTitle.COMPANY_METRICS.value) {
                companyMetricsList.value = viewModelList
            }
        }

        if (tileEntity.type == Enums.TileType.PHOTO_TILE_LIST.value) {
            val viewModelList: ArrayList<DashboardPhotoTileViewVM> = arrayListOf()
            for (tileContentEntity in tileContentEntityList) {
                viewModelList.add(
                    DashboardPhotoTileViewVM(
                        tileContentEntity
                    )
                )
            }

            if (tileEntity.title == Enums.TileTitle.YOUR_JOBS.value) {
                yourJobList.value = viewModelList
            }

            if (tileEntity.title == Enums.TileTitle.COLLEAGUE_JOBS.value) {
                colleagueJobList.value = viewModelList
            }
        }

    }

    private fun onRetrieveTileContentListError(e: Throwable){
        e.printStackTrace()
        errorMessage.value = R.string.server_error
    }

}