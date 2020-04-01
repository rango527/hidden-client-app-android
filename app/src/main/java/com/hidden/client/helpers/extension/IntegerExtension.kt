package com.hidden.client.helpers.extension

fun Int?.safeValue(): Int {
    return if (this === null) 0 else this
}