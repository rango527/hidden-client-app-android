package com.hidden.client.ui.activities.settings

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import com.hidden.client.R
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.datamodels.HCConsentResponse
import com.hidden.client.enums.Consent
import com.hidden.client.enums.UserType
import com.hidden.client.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCTermsOfServiceActivity : BaseActivity() {

    private lateinit var txtTerms: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_service)

        txtTerms = findViewById(R.id.text_value)

        initCloseButton()

        RetrofitClient.instance.getConsent(UserType.client.toString(), Consent.terms.toString())
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
                            txtTerms.setText(Html.fromHtml(response.body()!!.content, Html.FROM_HTML_MODE_COMPACT))
                        } else {
                            txtTerms.setText(Html.fromHtml(response.body()!!.content))
                        }
                    } else {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}
