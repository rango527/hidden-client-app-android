package com.hidden.client.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal


@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HCGlobal.getInstance().currentActivity = this
    }

    override fun finish() {
        super.finish()
        overridePendingVTransitionExit()
    }

    override fun onBackPressed() {
        super.finish()
        overridePendingVTransitionExit()
    }

    override fun onResume() {
        super.onResume()
        HCGlobal.getInstance().currentActivity = this
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation. Vertical
     */
    fun overridePendingVTransitionEnter() {
        overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation. Vertical
     */
    private fun overridePendingVTransitionExit() {
        overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation. Horizontal
     */
    fun initCloseButton() {
        val imgClose = findViewById<ImageView>(R.id.image_close)
        imgClose.setOnClickListener {
            finish()
        }
    }
}