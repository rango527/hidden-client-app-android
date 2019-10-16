package com.hidden.client.models

/**
{
    "client_id": "1",
    "is_admin": true,
    "full_name": "Hideo Den",
    "status": "APPROVED",
    "token": "782870fe7543596da15f258ec166e0fa"
}
*/

class HCClient {

    private var id: Int = 0
    private var isAdmin: Boolean = false
    private lateinit var fullName: String
    private lateinit var status: String
    private lateinit var token: String

    constructor() {

    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getIsAdmin(): Boolean {
        return isAdmin
    }

    fun setIsAdmin(isAdmin: Boolean) {
        this.isAdmin = isAdmin
    }

    fun getFullName(): String {
        return fullName
    }

    fun setFullName(fullName: String) {
        this.fullName = fullName
    }

    fun getStatus(): String {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getToken(): String {
        return token
    }

    fun getBearerToken(): String {
        return "Bearer " + token
    }

    fun setToken(token: String) {
        this.token = token
    }
}