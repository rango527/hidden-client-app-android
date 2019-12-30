package com.hidden.client.ui.listener

import android.app.Activity
import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Created by Jerry on 4/18/2018.
 */

class DetectSwipeGestureListener : GestureDetector.SimpleOnGestureListener() {

    /* This method is invoked when a swipe gesture happened. */
    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {

        // Get swipe delta value in x axis.
        val deltaX = e1.x - e2.x

        // Get swipe delta value in y axis.
        val deltaY = e1.y - e2.y

        // Get absolute value.
        val deltaXAbs = Math.abs(deltaX)
        val deltaYAbs = Math.abs(deltaY)

        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        if (deltaXAbs >= MIN_SWIPE_DISTANCE_X && deltaXAbs <= MAX_SWIPE_DISTANCE_X) {
            if (deltaX > 0) {

            } else {

            }
        }

        if (deltaYAbs >= MIN_SWIPE_DISTANCE_Y && deltaYAbs <= MAX_SWIPE_DISTANCE_Y) {
            if (deltaY > 0) {

            } else {

            }
        }


        return true
    }

    // Invoked when single tap screen.
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {

        return true
    }

    // Invoked when double tap screen.
    override fun onDoubleTap(e: MotionEvent): Boolean {

        return true
    }

    companion object {

        // Minimal x and y axis swipe distance.
        private val MIN_SWIPE_DISTANCE_X = 100
        private val MIN_SWIPE_DISTANCE_Y = 100

        // Maximal x and y axis swipe distance.
        private val MAX_SWIPE_DISTANCE_X = 1000
        private val MAX_SWIPE_DISTANCE_Y = 1000
    }
}