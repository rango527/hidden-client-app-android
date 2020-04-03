package com.hidden.client.ui.dialogs

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.hidden.client.R
import kotlinx.android.synthetic.main.toast_layout.view.*

class HToast {

    companion object {

        val TOAST_SUCCESS = 1;
        val TOAST_ERROR = 2;

        private lateinit var layoutInflater: LayoutInflater

        fun show(context: Activity, message: String, toastType: Int) {
            layoutInflater = LayoutInflater.from(context)
            val layout = layoutInflater.inflate(R.layout.toast_layout, (context).findViewById(R.id.layout_toast))

            if (toastType == TOAST_SUCCESS) {
                layout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen_1))
            } else if (toastType == TOAST_ERROR) {
                layout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed_2))
            }

            layout.text_toast.text = message
            val toast = Toast(context.applicationContext)
            toast.duration = Toast.LENGTH_SHORT

            toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.TOP, 0, 0)

            toast.view = layout //setting the view of custom toast layout
            toast.show()
        }
    }
}