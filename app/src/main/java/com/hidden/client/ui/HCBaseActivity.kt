package com.hidden.client.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal

open class HCBaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HCGlobal.getInstance(this).g_currentActivity = this
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
        HCGlobal.getInstance(this).g_currentActivity = this
    }
    /**
     * Ovverides the pending Activity transition by performing the "Enter" animation. Vertical
     */
    public fun overridePendingVTransitionEnter() {
        overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation. Vertical
     */
    public fun overridePendingVTransitionExit() {
        overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom)
    }

    /**
     * Ovverides the pending Activity transition by performing the "Enter" animation. Horizontal
     */
    public fun overridePendingHTransitionEnter() {
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation. Horizontal
     */
    public fun overridePendingHTransitionExit() {
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
    }

    fun initCloseButton() {
        var imgClose: ImageView = findViewById(R.id.image_close);
        imgClose.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
}