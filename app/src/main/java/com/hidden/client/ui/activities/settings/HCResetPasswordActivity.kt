package com.hidden.client.ui.activities.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hidden.client.R
import com.hidden.client.ui.HCBaseActivity

class HCResetPasswordActivity : HCBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        initCloseButton()
    }
}
