package com.hidden.client.datamodels

data class HCErrorResponse(
    val errors: Array<String>,
    val stat: Int) {
}