package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hidden.client.R
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.extension.safeValue

class HCSplashActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 500   // 3 seconds (0.5 SEC FOR TEST)

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            if (AppPreferences.apiAccessToken.safeValue().isNotEmpty() && AppPreferences.myFullName.safeValue().isNotEmpty() && AppPreferences.myId.safeValue() != 0) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize the Handler
        mDelayHandler = Handler()

        // Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
