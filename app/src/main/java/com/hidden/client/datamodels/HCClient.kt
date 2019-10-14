package com.hidden.client.datamodels

/**
{
    "client_id": "1",
    "is_admin": true,
    "full_name": "Hideo Den",
    "status": "APPROVED",
    "token": "782870fe7543596da15f258ec166e0fa"
}
*/

data class HCClient(
    val id: Int,
    val is_admin: Boolean,
    val full_name: String,
    val status: String,
    val token: String) {

}