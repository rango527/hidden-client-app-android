package com.hidden.client.helpers

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD


object HCDialog {

    fun KProgressDialog(context: Context): KProgressHUD {

        return KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
    }

}