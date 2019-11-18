package com.hidden.client.ui.viewmodels.event

import android.content.Intent

interface ViewMessages {
    fun showMessage(message: String)
}

interface ViewNavigation {

    fun startActivity(intent: Intent?)
    fun startActivityForResult(intent: Intent?, requestCode: Int)
}