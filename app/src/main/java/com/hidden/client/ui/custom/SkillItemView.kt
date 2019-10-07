package com.hidden.client.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.hidden.client.R

class SkillItemView : LinearLayout {

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        inflate(context, R.layout.skill_item_view, this)
        val textSkill: TextView = findViewById(R.id.text_skill)

        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.SkillItemView)
            textSkill.text = attributes.getString(R.styleable.SkillItemView_skill)
            attributes.recycle()
        }
    }

    constructor(context: Context, skill:String? = null, proficiency: Int? = null) : super(context) {
        inflate(context, R.layout.skill_item_view, this)
        if (skill != null) {
            val textSkill: TextView = findViewById(R.id.text_skill)
            textSkill.text = skill
        }
    }

}