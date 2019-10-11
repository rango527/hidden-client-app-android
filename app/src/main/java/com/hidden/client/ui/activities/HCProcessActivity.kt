package com.hidden.client.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.widget.*
import androidx.core.content.ContextCompat
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.animation.TransformAnimation
import com.hidden.client.ui.fragments.process.HCMessageFragment
import com.hidden.client.ui.fragments.process.HCProcessFragment
import java.lang.Math.round

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

                var density: Float = applicationContext.resources.displayMetrics.density

                layoutTitle.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

                var animation: Animation = TransformAnimation(layoutTitle, layoutTitle.height, layoutTitle.measuredHeight)
                animation.setInterpolator (AccelerateInterpolator())
                animation.duration = 50

                animation.setAnimationListener(object: Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        HCGlobal.getInstance(this@HCProcessActivity).HCLog("Animation end")

                        val param = layoutTitle.layoutParams as LinearLayout.LayoutParams
                        param.topMargin = resources.getDimension(R.dimen.margin_default).toInt()
                        layoutTitle.layoutParams = param
                    }

                })
                layoutTitle.animation = animation
                layoutTitle.startAnimation(animation)

                supportFragmentManager.beginTransaction().replace(R.id.fragment_process, HCProcessFragment()).commit()
            }

            R.id.text_message -> {
                textBtnProcess.setBackgroundResource(android.R.color.transparent)
                textBtnProcess.setTextColor(ContextCompat.getColor(this, R.color.colorWhite_1))

                textBtnMessage.setBackgroundResource(R.drawable.panel_top_rounded_border_small)
                textBtnMessage.setTextColor(ContextCompat.getColor(this, R.color.colorBlack_2))

                var density: Float = applicationContext.resources.displayMetrics.density

                var animation: Animation = TransformAnimation(layoutTitle, layoutTitle.height, round(40 * density))
                animation.setInterpolator (AccelerateInterpolator())
                animation.duration = 100

                animation.setAnimationListener(object: Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        HCGlobal.getInstance(this@HCProcessActivity).HCLog("Animation end")

                        val param = layoutTitle.layoutParams as LinearLayout.LayoutParams
                        param.topMargin = resources.getDimension(R.dimen.list_row_margin_default).toInt()
                        layoutTitle.layoutParams = param
                    }

                })
                layoutTitle.animation = animation
                layoutTitle.startAnimation(animation)

                supportFragmentManager.beginTransaction().replace(R.id.fragment_process, HCMessageFragment()).commit()
            }
            R.id.button_back -> {
                finish()
            }
        }
    }

}
