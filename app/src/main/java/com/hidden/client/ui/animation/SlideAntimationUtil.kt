package com.hidden.client.ui.animation

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.hidden.client.R

class SlideAntimationUtil {

    companion object {

        /**
         * Animates a  view so that it slides in from the left of  it's container
         *
         * @param context
         * @param view
         */
        public fun slideInFromLeft(context: Context, view: View) {
            runSimpleAnimation(context, view, R.anim.anim_slide_in_left)
        }

        /**
         * Animates a  view so that it slides from its current position, out of  view to the left
         *
         * @param context
         * @param view
         */
        public fun slideOutToLeft(context: Context, view: View) {
            runSimpleAnimation(context, view, R.anim.anim_slide_out_left)
        }

        /**
         * Animates a  view so that it slides fin the from the right of it's container
         *
         * @param context
         * @param view
         */
        public fun slideInFromRight(context: Context, view: View) {
            runSimpleAnimation(context, view, R.anim.anim_slide_in_right)
        }

        /**
         * Animates a  view so that it slides from its current position, out of view to the right
         *
         * @param context
         * @param view
         */
        public fun slideOutToRight(context: Context, view: View) {
            runSimpleAnimation(context, view, R.anim.anim_slide_out_right)
        }

        /**
         * Runs a simple animation on a View with no extra  parameters.
         *
         * @param context
         * @param view
         * @param animationId
         */
        private fun runSimpleAnimation(context: Context, view: View, animationId: Int) {
            view.startAnimation(AnimationUtils.loadAnimation(
                context, animationId
            ))
        }
    }
}