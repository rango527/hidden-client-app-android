package com.hidden.client.ui.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class TransformAnimation(
    private var view: View,
    private var fromHeight: Int,
    private var toHeight: Int
) : Animation() {

    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
        val newHeight: Int

        if (view.height != toHeight) {
            newHeight = (fromHeight + (toHeight - fromHeight) * interpolatedTime).toInt()
            view.layoutParams.height = newHeight

            view.requestLayout()
        }


    }

    override fun willChangeBounds(): Boolean {
        return true
    }

}