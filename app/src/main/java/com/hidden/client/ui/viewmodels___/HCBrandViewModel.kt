package com.hidden.client.ui.viewmodels___

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hidden.client.models_.HCBrand

class HCBrandViewModel(private var brand: HCBrand) : ViewModel() {

    fun getBrand(): HCBrand {
        return this.brand
    }

    var brandListMutableLiveData = MutableLiveData<ArrayList<HCBrandViewModel>>()
    var brandList = ArrayList<HCBrandViewModel>()

    fun getBrandList(): MutableLiveData<ArrayList<HCBrandViewModel>> {

        return brandListMutableLiveData
    }

    fun setBrandList(brandList: List<HCBrand>) {

        this.brandList.clear()

        for (brand in brandList) {
            this.brandList.add(HCBrandViewModel(brand))
        }

        brandListMutableLiveData.value = this.brandList
    }
}