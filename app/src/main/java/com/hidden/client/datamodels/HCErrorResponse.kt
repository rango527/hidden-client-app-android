package com.hidden.client.datamodels

/**
{
    "errors": [
        "Access denied"
    ],
    "stat": 401
}
*/

data class HCErrorResponse(
    val errors: Array<String>,
    val stat: Int) {
}