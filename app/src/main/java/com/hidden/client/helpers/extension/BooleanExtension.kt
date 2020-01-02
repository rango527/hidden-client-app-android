package com.hidden.client.helpers.extension

fun Boolean?.safeValue(): Boolean {
    return if (this === null) false else this
}