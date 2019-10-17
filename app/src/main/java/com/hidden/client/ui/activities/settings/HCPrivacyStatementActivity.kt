package com.hidden.client.ui.activities.settings

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import com.hidden.client.R
import com.hidden.client.apis.RetrofitClient
import com.hidden.client.datamodels.HCConsentResponse
import com.hidden.client.enums.Consent
import com.hidden.client.enums.UserType
import com.hidden.client.ui.HCBaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCPrivacyStatementActivity : HCBaseActivity() {

    private lateinit var txtPrivacy: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_statement)

        txtPrivacy = findViewById(R.id.text_value)

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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txtPrivacy.setText(Html.fromHtml(response.body()!!.content, Html.FROM_HTML_MODE_COMPACT))
                        } else {
                            txtPrivacy.setText(Html.fromHtml(response.body()!!.content))
                        }
                    } else {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}
