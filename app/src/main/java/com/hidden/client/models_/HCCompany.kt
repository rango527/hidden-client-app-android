package com.hidden.client.models_

class HCCompany {

    private var companyId: Int? = 0

    private var companyName: String? = ""
    private var companyPhotoUrl: String? = ""

    constructor() { }

    constructor(companyId: Int?, companyName: String?, companyPhotoUrl: String?) {
        setCompanyId(companyId)
        setCompanyName(companyName)
        setCompanyPhotoUrl(companyPhotoUrl)
    }

    fun getCompanyId(): Int? {
        return companyId
    }

    fun setCompanyId(companyId: Int?) {
        this.companyId = companyId
    }

    fun getCompanyName(): String? {
        return companyName
    }

    fun setCompanyName(companyName:  String?) {
        this.companyName = companyName
    }

    fun getCompanyPhotoUrl(): String? {
        return companyPhotoUrl
    }

    fun setCompanyPhotoUrl(companyPhotoUrl: String?) {
        this.companyPhotoUrl = companyPhotoUrl
    }

}