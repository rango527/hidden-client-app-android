package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.models.custom.GetAllJob
import com.hidden.client.models.entity.BrandEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class BrandViewVM: RootVM() {

    private val brand = MutableLiveData<BrandEntity>()

    fun bind(brand: BrandEntity) {
        this.brand.value = brand
    }

    fun getBrand(): MutableLiveData<BrandEntity> {
        return brand
    }
}