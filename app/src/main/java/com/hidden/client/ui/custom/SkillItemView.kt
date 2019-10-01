package com.hidden.client.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.hidden.client.R

class SkillItemView (context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.skill_item_view, this)

        val textSkill: TextView = findViewById(R.id.text_skill)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SkillItemView)
        textSkill.text = attributes.getString(R.styleable.SkillItemView_skill)
        attributes.recycle()

    }
}