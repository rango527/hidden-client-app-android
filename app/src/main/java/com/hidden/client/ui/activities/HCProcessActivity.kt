package com.hidden.client.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.widget.*
import androidx.core.content.ContextCompat
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.animation.SlideAnimation
import com.hidden.client.ui.fragments.process.HCMessageFragment
import com.hidden.client.ui.fragments.process.HCProcessFragment

class HCProcessActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textBtnProcess: TextView
    private lateinit var textBtnMessage: TextView

    private lateinit var imgPhoto: ImageView
    private lateinit var layoutTitle: LinearLayout
    private lateinit var textName: TextView
    private lateinit var textFor: TextView

    private lateinit var buttonBack: ImageButton

    private lateinit var fragmentProcess: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process)

        fragmentProcess = findViewById(R.id.fragment_process)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_process, HCProcessFragment()).commit()
        }

        // Init View
        imgPhoto = findViewById(R.id.img_photo)
        textName = findViewById(R.id.text_name)
        textFor = findViewById(R.id.text_for)
        layoutTitle = findViewById(R.id.layout_title)

        textBtnProcess = findViewById(R.id.text_process)
        textBtnProcess.setOnClickListener(this)

        textBtnMessage = findViewById(R.id.text_message)
        textBtnMessage.setOnClickListener(this)

        buttonBack = findViewById(R.id.button_back)
        buttonBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.text_process -> {
                textBtnProcess.setBackgroundResource(R.drawable.panel_top_rounded_border_small)
                textBtnProcess.setTextColor(ContextCompat.getColor(this, R.color.colorBlack_2))

                textBtnMessage.setBackgroundResource(android.R.color.transparent)
                textBtnMessage.setTextColor(ContextCompat.getColor(this, R.color.colorWhite_1))

                textName.visibility = View.VISIBLE
                textFor.visibility = View.VISIBLE
                imgPhoto.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

                val param = imgPhoto.layoutParams as LinearLayout.LayoutParams
                param.topMargin = resources.getDimension(R.dimen.margin_default).toInt()
                imgPhoto.layoutParams = param

                supportFragmentManager.beginTransaction().replace(R.id.fragment_process, HCProcessFragment()).commit()
            }

            R.id.text_message -> {
                textBtnProcess.setBackgroundResource(android.R.color.transparent)
                textBtnProcess.setTextColor(ContextCompat.getColor(this, R.color.colorWhite_1))

                textBtnMessage.setBackgroundResource(R.drawable.panel_top_rounded_border_small)
                textBtnMessage.setTextColor(ContextCompat.getColor(this, R.color.colorBlack_2))

                layoutTitle.visibility = View.INVISIBLE

                var animation: Animation = SlideAnimation(layoutTitle, layoutTitle.height, 0)
                animation.setInterpolator (AccelerateInterpolator())
                animation.duration = 100
                layoutTitle.animation = animation
                layoutTitle.startAnimation(animation)

//                layoutTitle.visibility = View.GONE

//                layoutTitle.animate()
//                    .scaleY(0.0f)
//                    .setDuration(2000)
//                    .setListener(object: AnimatorListenerAdapter() {
//                        override fun onAnimationEnd(animation: Animator) {
//                            super.onAnimationEnd(animation)
//                            layoutTitle.visibility = View.GONE
//                        }
//                    })

//                val anim: ValueAnimator = ValueAnimator.ofInt(layoutTitle.measuredHeight, -300);
//                anim.addUpdateListener { object: ValueAnimator.AnimatorUpdateListener {
//                    override fun onAnimationUpdate(valueAnimator: ValueAnimator) {
//                        var newHeight: Int = valueAnimator.animatedValue as Int
//                        var layoutParams: ViewGroup.LayoutParams = layoutTitle.layoutParams
//                        layoutParams.height = newHeight
//                        layoutTitle.layoutParams = layoutParams
//                    }
//                } }
//                anim.duration = 2000
//                anim.start()

//                var density: Float = applicationContext.resources.displayMetrics.density
//                imgPhoto.layoutParams.height = Math.round(40 * density)
//
//                val param = imgPhoto.layoutParams as LinearLayout.LayoutParams
//                param.topMargin = resources.getDimension(R.dimen.list_row_margin_default).toInt()
//
//                imgPhoto.layoutParams = param

//                supportFragmentManager.beginTransaction().replace(R.id.fragment_process, HCMessageFragment()).commit()
            }
            R.id.button_back -> {
                finish()
            }
        }
    }

}
