package com.hidden.client.ui.activities.shortlist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.hidden.client.R
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.HomeActivity
import com.hidden.client.ui.fragments.home.shortlists.ShortlistsFragment

class ShortlistJobFilterActivity : BaseActivity(), View.OnClickListener {
    private lateinit var layoutFilterContainer: LinearLayout
    private lateinit var layoutFilterPanel: ConstraintLayout
    private lateinit var imgOpenFilter: ImageView
    private lateinit var imgCloseFilter: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shortlist_job_filter)
        layoutFilterContainer = findViewById(R.id.layout_filter_container)
        layoutFilterPanel = findViewById(R.id.layout_filter_panel)
        imgCloseFilter = findViewById(R.id.img_close_filter_layout)
        imgCloseFilter.setOnClickListener(this)

        // slide-up animation
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_top)

        layoutFilterPanel.visibility = View.VISIBLE
        layoutFilterPanel.startAnimation(slideUp)

//        (this as ).toggleMask()

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_close_filter_layout -> {

                // slide-down animation
                val slideDown = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_top)
                layoutFilterPanel.startAnimation(slideDown)
                layoutFilterPanel.visibility = View.INVISIBLE

                layoutFilterContainer.animate()
                    .alpha(1f)
                    .setDuration(200)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            layoutFilterContainer.visibility = View.GONE
//                            (this as HomeActivity).toggleMask()
                        }
                    })
            }
        }
    }

}