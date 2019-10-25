package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models.HCJob
import com.hidden.client.DataBinderMapperImpl
import com.hidden.client.datamodels.HCMetricsResponse
import com.hidden.client.models.HCBrand

class HCMetricViewModel: ViewModel {

    private lateinit var metric: HCMetricsResponse

    constructor() : super()

    constructor(metric: HCMetricsResponse) : super() {
        this.metric = metric
    }

    fun getBrand(): HCMetricsResponse {
        return this.metric
    }

    var metricListMutableLiveData = MutableLiveData<ArrayList<HCMetricViewModel>>()
    var metricList = ArrayList<HCMetricViewModel>()

    fun getMetricList(): MutableLiveData<ArrayList<HCMetricViewModel>> {

        return metricListMutableLiveData
    }

    fun setMetricList(metricList: List<HCMetricsResponse>) {

        this.metricList.clear()

        for (metric in metricList) {
            this.metricList.add(HCMetricViewModel(metric))
        }

        metricListMutableLiveData.value = this.metricList
    }
}