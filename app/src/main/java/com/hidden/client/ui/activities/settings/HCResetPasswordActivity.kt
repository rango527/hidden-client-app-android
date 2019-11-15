package com.hidden.client.ui.activities.settings

import android.os.Bundle
import com.hidden.client.R
import com.hidden.client.ui.BaseActivity

class HCResetPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        initCloseButton()
    }
}
