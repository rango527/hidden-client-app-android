package com.hidden.client.ui.animation

import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import com.hidden.client.helpers.HCGlobal

class SlideAnimation: Animation {

    private var fromHeight: Int
    private var toHeight: Int
    private var view: View

    constructor(view: View, fromHeight: Int, toHeight: Int) {
        this.fromHeight = fromHeight
        this.toHeight = toHeight
        this.view = view
    }

    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
        val newHeight: Int

        if (view.height !== toHeight) {
            newHeight = (fromHeight + (toHeight - fromHeight) * interpolatedTime).toInt()
            view.layoutParams.height = newHeight

            view.requestLayout()
        }
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun willChangeBounds(): Boolean {
        return true
    }

    override fun setAnimationListener(listener: AnimationListener?) {
        super.setAnimationListener(listener)
    }

}