package com.hidden.client.ui.fonts

import android.widget.TextView
import android.view.ViewGroup
import android.graphics.Typeface
import android.content.res.AssetManager
import android.view.View


class FontChangeCrawler {
    private var typeface: Typeface? = null

    constructor(typeface: Typeface) {
        this.typeface = typeface
    }

    constructor(assets: AssetManager, assetsFontFileName: String) {
        typeface = Typeface.createFromAsset(assets, assetsFontFileName)
    }

    fun replaceFonts(viewTree: ViewGroup) {
        var child: View
        for (i in 0 until viewTree.childCount) {
            child = viewTree.getChildAt(i)
            if (child is ViewGroup) {
                // recursive call
                replaceFonts(child as ViewGroup)
            } else if (child is TextView) {
                // base case
                child.typeface = typeface
            }
        }
    }
}