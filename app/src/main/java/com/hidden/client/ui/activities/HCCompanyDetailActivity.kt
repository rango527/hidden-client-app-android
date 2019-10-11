package com.hidden.client.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.hidden.client.R
import com.hidden.client.ui.HCBaseActivity

class HCCompanyDetailActivity : HCBaseActivity(), View.OnClickListener {

    private lateinit var imgClose:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_detail)

        imgClose = findViewById(R.id.img_close)
        imgClose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.img_close -> {
                finish()
            }
        }
    }
}
