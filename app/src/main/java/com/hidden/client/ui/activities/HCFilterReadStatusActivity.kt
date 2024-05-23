package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal

class HCFilterReadStatusActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonBack: ImageView
    private lateinit var imgTickHasUnreadMessages: ImageView
    private lateinit var imgTickHasNoUnreadMessages: ImageView
    private lateinit var layoutFilterHasUnreadMessages: ConstraintLayout
    private lateinit var layoutFilterHasNoUnreadMessages: ConstraintLayout
    private lateinit var buttonDone: Button

    private var readStatus: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_read_status)

        buttonBack = findViewById(R.id.img_back)
        buttonBack.setOnClickListener(this)

        buttonDone = findViewById(R.id.button_filter_done)
        buttonDone.setOnClickListener(this)

        layoutFilterHasUnreadMessages = findViewById(R.id.layout_filter_has_unread_messages)
        layoutFilterHasUnreadMessages.setOnClickListener(this)

        layoutFilterHasNoUnreadMessages = findViewById(R.id.layout_filter_has_no_unread_messages)
        layoutFilterHasNoUnreadMessages.setOnClickListener(this)

        imgTickHasUnreadMessages = findViewById(R.id.img_tick_has_unread_messages)
        imgTickHasNoUnreadMessages = findViewById(R.id.img_tick_has_no_unread_messages)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {
                finish()
            }

            R.id.button_filter_done -> {
                HCGlobal.getInstance().tempProcessFilterList.currentReadStatus = readStatus

                val intent = Intent(HCGlobal.getInstance().currentActivity, HCProcessFilterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }

            R.id.layout_filter_has_unread_messages -> {
                imgTickHasUnreadMessages.setImageResource(R.drawable.tick_on)
                imgTickHasNoUnreadMessages.setImageResource(R.drawable.tick_off)
                readStatus = 1
            }

            R.id.layout_filter_has_no_unread_messages -> {
                imgTickHasUnreadMessages.setImageResource(R.drawable.tick_off)
                imgTickHasNoUnreadMessages.setImageResource(R.drawable.tick_on)
                readStatus = 0
            }
        }
    }
}
