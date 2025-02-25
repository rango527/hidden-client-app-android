package com.hidden.client.ui.activities.settings

import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import com.hidden.client.R
import com.hidden.client.datamodels.HCConsentResponse
import com.hidden.client.enums.Consent
import com.hidden.client.enums.UserType
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_privacy_statement.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCPrivacyStatementActivity : BaseActivity() {

    private lateinit var txtPrivacy: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_statement)

        txtPrivacy = findViewById(R.id.webview)

        initCloseButton()

        RetrofitClient.instance.getConsent(UserType.client.toString(), Consent.privacy.toString())
            .enqueue(object: Callback<HCConsentResponse> {
                override fun onFailure(call: Call<HCConsentResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<HCConsentResponse>,
                    response: Response<HCConsentResponse>
                ) {
                    if (response.isSuccessful) {
                        webview.loadData(response.body()!!.content, "text/html; charset=utf-8", "UTF-8")
                    } else {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}
