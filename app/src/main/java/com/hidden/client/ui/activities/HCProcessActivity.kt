package com.hidden.client.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.ui.fragments.home.shortlists.HCShortlistsFragment
import com.hidden.client.ui.fragments.process.HCMessageFragment
import com.hidden.client.ui.fragments.process.HCProcessFragment
import kotlinx.android.synthetic.main.activity_process.*
import kotlinx.android.synthetic.main.activity_splash.*

class HCProcessActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textBtnProcess: TextView
    private lateinit var textBtnMessage: TextView

    private lateinit var imgPhoto: ImageView
    private lateinit var textName: TextView
    private lateinit var textFor: TextView

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

        textBtnProcess = findViewById(R.id.text_process)
        textBtnProcess.setOnClickListener(this)

        textBtnMessage = findViewById(R.id.text_message)
        textBtnMessage.setOnClickListener(this)

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

                textName.visibility = View.GONE
                textFor.visibility = View.GONE

                var density: Float = applicationContext.resources.displayMetrics.density
                imgPhoto.layoutParams.height = Math.round(40 * density)

                val param = imgPhoto.layoutParams as LinearLayout.LayoutParams
                param.topMargin = resources.getDimension(R.dimen.list_row_margin_default).toInt()
                imgPhoto.layoutParams = param

                supportFragmentManager.beginTransaction().replace(R.id.fragment_process, HCMessageFragment()).commit()
            }
        }
    }

}
