package com.hidden.client.helpers

import android.app.AlertDialog

abstract class HCBaseDialogHelper {
    abstract val builder: AlertDialog.Builder

    // required bools
    open var cancelable: Boolean = true
    open var isBackgroundTransient: Boolean = true

    // dialog
    open var dialog: AlertDialog? = null

    // dialog create

}