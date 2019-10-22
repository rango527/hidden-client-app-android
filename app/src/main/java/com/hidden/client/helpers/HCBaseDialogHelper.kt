package com.hidden.client.helpers

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

abstract class HCBaseDialogHelper {
    abstract val builder: AlertDialog.Builder

    // required bools
    open var cancelable: Boolean = true
    open var isBackgroundTransient: Boolean = true

    // dialog
    open var dialog: AlertDialog? = null

    // dialog create
    open fun create(): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()

        // very much needed for customised dialogs
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog!!
    }

    // cancel listener
    open fun onCancelListener(func: () -> Unit): AlertDialog.Builder? =
        builder.setOnCancelListener {
            func()
        }
}