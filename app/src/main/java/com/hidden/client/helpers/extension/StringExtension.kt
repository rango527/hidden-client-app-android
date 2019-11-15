package com.hidden.client.helpers.extension

fun String.isEmailValid(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}