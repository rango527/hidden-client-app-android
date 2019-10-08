package com.hidden.client.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.LinearLayout

class RoundedCornerLayout : LinearLayout {

    companion object {
        var CORNER_RADIUS = 20.0F
    }

    private lateinit var maskBitmap: Bitmap
    private lateinit var paint: Paint
    private lateinit var maskPaint: Paint
    private var cornerRadius: Float = 0f

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        var metrics: DisplayMetrics = context.resources.displayMetrics
        cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CORNER_RADIUS, metrics)

        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        maskPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        maskPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))

        setWillNotDraw(false)
    }

    override fun draw(canvas: Canvas) {
        var offscreenBitmap = Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
        var offscreenCanvas = Canvas(offscreenBitmap)

        super.draw(offscreenCanvas)

        if (maskBitmap == null) {
            maskBitmap = createMask(canvas.width, canvas.height)
        }

        offscreenCanvas.drawBitmap(maskBitmap, 0f, 0f, maskPaint)
        canvas.drawBitmap(offscreenBitmap, 0f, 0f, paint)
    }

    private fun createMask(width:Int, height: Int) : Bitmap {
        var mask: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        var canvas: Canvas = Canvas(mask)

        return mask
    }
}