package com.hidden.client.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hidden.client.R

class HCTabBar : LinearLayout, View.OnClickListener {

    private lateinit var view: View
    private var curSel: Int = 1
    private var listener: OnTabSelectedListener? = null

    private var unSelectedTabImages: Array<Int> = arrayOf(
        R.drawable.menu_dashboard_empty, R.drawable.menu_shortlists_empty, R.drawable.menu_processes_empty)
    private var selectedTabImages: Array<Int> = arrayOf(
        R.drawable.menu_dashboard, R.drawable.menu_shortlists_filled, R.drawable.menu_processes_filled)

    interface OnTabSelectedListener {
        fun onTabSelected(num: Int)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {

        view = LayoutInflater.from(context).inflate(R.layout.main_tabbar_view, this, true)

        for (i in 0..2) {
            view.findViewWithTag<View>("tab$i").setOnClickListener(this)
        }
        selectAt(0, false)
        selectAt(1, true)
        selectAt(2, false)
    }

    override fun onClick(view: View) {
        val tag = view.tag as String
        if (tag.contains("tab")) {
            val num = Integer.valueOf(tag.substring(3))
            if (curSel == num) return
            selectAt(curSel, false)
            selectAt(num, true)
            curSel = num
        }
    }

    fun processTag(num: Int) {
        if (curSel == num) return
        selectAt(curSel, false)
        selectAt(num, true)
        curSel = num
    }

    private fun selectAt(num: Int, sel: Boolean) {
        val iv = view.findViewWithTag<ImageView>("tabIv$num")
        val tv = view.findViewWithTag<TextView>("tabTv$num")

        val color: Int = ContextCompat.getColor(context, if (sel) R.color.colorBlack_1 else R.color.colorGray_3)
        val img: Int = if (sel) selectedTabImages[num] else unSelectedTabImages[num]

        iv.setImageResource(img)
        tv.setTextColor(color)

        if (sel && listener != null) {
            listener!!.onTabSelected(num)
        }
    }

    fun setSelectedListener(listener: OnTabSelectedListener) {
        this.listener = listener
    }
}
