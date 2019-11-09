package com.hidden.client.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.datamodels.HCMetricsResponse
import com.hidden.client.enums.MetricType

class HCMetricViewModel: ViewModel {

    private lateinit var title: String
    private lateinit var value: String

    private lateinit var metricType: String
    private lateinit var colorSchema: String

    constructor() : super()

    constructor(title: String, value: String, metricType: String, colorSchema: String) : super() {
        this.title = title
        this.value = value
        this.metricType = metricType
        this.colorSchema = colorSchema
    }

    fun getTitle(): String {
        return this.title
    }

    fun getValue(): String {
        return  this.value
    }

    fun getColorSchema(): String {
        return this.colorSchema
    }

    fun getMetricType(): String {
        return this.metricType
    }

    var yourMetricListMutableLiveData = MutableLiveData<ArrayList<HCMetricViewModel>>()
    var yourMetricList = ArrayList<HCMetricViewModel>()

    var companyMetricListMutableLiveData = MutableLiveData<ArrayList<HCMetricViewModel>>()
    var companyMetricList = ArrayList<HCMetricViewModel>()

    fun getMetricList(metricType: String): MutableLiveData<ArrayList<HCMetricViewModel>> {

        return when (metricType) {
            MetricType.YOUR_METRIC.value -> {
                yourMetricListMutableLiveData
            }
            MetricType.COMPANY_METRIC.value -> {
                companyMetricListMutableLiveData
            }
            else -> {
                yourMetricListMutableLiveData
            }
        }
    }

    fun setMetricList(metricList: List<HCMetricsResponse>, metricType: String, colorSchema: String) {

        var tempMetricList: ArrayList<HCMetricViewModel> = arrayListOf();

        for (metric in metricList) {
            tempMetricList.add(HCMetricViewModel(metric.title, metric.value, metricType, colorSchema))
        }

        if (metricType == MetricType.YOUR_METRIC.value) {

            this.yourMetricList.clear()
            this.yourMetricList.addAll(tempMetricList)

            this.yourMetricListMutableLiveData.value = this.yourMetricList

        } else if (metricType == MetricType.COMPANY_METRIC.value) {

            this.companyMetricList.clear()
            this.companyMetricList.addAll(tempMetricList)

            this.companyMetricListMutableLiveData.value = this.companyMetricList
        }
    }
}