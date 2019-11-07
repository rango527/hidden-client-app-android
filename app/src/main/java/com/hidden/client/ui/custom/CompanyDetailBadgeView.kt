package com.hidden.client.ui.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hidden.client.R

class CompanyDetailBadgeView : LinearLayout {

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {

        inflate(context, R.layout.company_detail_badge_view, this)

        var layoutBackground: LinearLayout = findViewById(R.id.layout_background)
        val textTitle: TextView = findViewById(R.id.text_title)
        val imgIcon: ImageView = findViewById(R.id.img_icon)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CompanyDetailBadge)

        textTitle.text = attributes.getString(R.styleable.CompanyDetailBadge_badge_title)

        var iconDrawable: Drawable? = attributes.getDrawable(R.styleable.CompanyDetailBadge_badge_icon)
        if (iconDrawable != null) {
            imgIcon.setImageDrawable(iconDrawable)
        }

        var backgroundDrawable: Drawable? = attributes.getDrawable(R.styleable.CompanyDetailBadge_badge_color)
        if (backgroundDrawable != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                layoutBackground.background = backgroundDrawable
            } else {
                @Suppress("DEPRECATION")
                layoutBackground.setBackgroundDrawable(backgroundDrawable)
            }
        }

        attributes.recycle()
    }

    constructor(context: Context, text:String? = null, icon:Int? = null, bgColor: Int? = null) : super(context) {
        inflate(context, R.layout.company_detail_badge_view, this)
        if (text !== null) {
            var layoutBackground: LinearLayout = findViewById(R.id.layout_background)
            val textTitle: TextView = findViewById(R.id.text_title)
            val imgIcon: ImageView = findViewById(R.id.img_icon)

            textTitle.text = text
            layoutBackground.setBackgroundResource(bgColor!!)
            imgIcon.setImageResource(icon!!)
        }
    }
}