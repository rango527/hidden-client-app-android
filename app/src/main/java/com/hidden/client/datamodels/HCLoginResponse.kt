package com.hidden.client.datamodels

data class HCLoginResponse(
    val id: Int,
    val is_admin: Boolean,
    val full_name: String,
    val status: String,
    val token: String) {

}