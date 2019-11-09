package com.hidden.client.models_

class HCClient {
    private var clientId: Int? = 0

    private var clientFullName: String? = ""
    private var clientEmail: String? = ""
    private var clientJobTitle: String? = ""

    private var clientPhotoUrl: String? = ""

    private var createdAt: String? = ""
    private var updatedAt: String? = ""

    private var isAdmin: Boolean? = false

    private var company: HCCompany? = HCCompany()

    constructor(
        clientId: Int?,
        clientFullName: String?,
        clientEmail: String?,
        clientJobTitle: String?,
        clientPhotoUrl: String?,
        createdAt: String?,
        updatedAt: String?,
        isAdmin: Boolean?,
        company: HCCompany?
    ) {
        setClientId(clientId)
        setClientFullName(clientFullName)
        setClientEmail(clientEmail)
        setClientJobTitle(clientJobTitle)
        setClientPhotoUrl(clientPhotoUrl)
        setCreatedAt(createdAt)
        setUpdatedAt(updatedAt)
        setIsAdmin(isAdmin)
        setCompany(company)
    }

    fun getClientId(): Int? {
        return clientId
    }

    fun setClientId(clientId: Int?) {
        this.clientId = clientId
    }

    fun getClientFullName(): String? {
        return clientFullName
    }

    fun setClientFullName(clientFullName: String?) {
        this.clientFullName = clientFullName
    }

    fun getClientEmail(): String? {
        return clientEmail
    }

    fun setClientEmail(clientEmail: String?) {
        this.clientEmail = clientEmail
    }

    fun getClientJobTitle(): String? {
        return clientJobTitle
    }

    fun setClientJobTitle(clientJobTitle: String?) {
        this.clientJobTitle = clientJobTitle
    }

    fun getClientPhotoUrl(): String? {
        return clientPhotoUrl
    }

    fun setClientPhotoUrl(clientPhotoUrl: String?) {
        this.clientPhotoUrl = clientPhotoUrl
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt
    }

    fun getIsAdmin(): Boolean? {
        return isAdmin
    }

    fun setIsAdmin(isAdmin: Boolean?) {
        this.isAdmin = isAdmin
    }

    fun getCompany(): HCCompany? {
        return company
    }

    fun setCompany(company: HCCompany?) {
        this.company = company
    }
}