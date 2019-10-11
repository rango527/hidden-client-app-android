package com.hidden.client.ui

import androidx.appcompat.app.AppCompatActivity
import com.hidden.client.R

open class HCBaseActivity: AppCompatActivity() {

    override fun finish() {
        super.finish()
        overridePendingVTransitionExit()
    }

    override fun onBackPressed() {
        super.finish()
        overridePendingVTransitionExit()
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
}