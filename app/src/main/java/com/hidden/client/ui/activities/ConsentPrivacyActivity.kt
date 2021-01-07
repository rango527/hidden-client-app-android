package com.hidden.client.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.hidden.client.R
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.FeedbackQuestionEntity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.JobListVM
import com.hidden.client.ui.viewmodels.main.ShortlistListVM
import kotlinx.android.synthetic.main.activity_privacy_statement.*
import okhttp3.MediaType
import okhttp3.RequestBody

class ConsentPrivacyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel: ShortlistListVM
    private lateinit var txtPrivacy: WebView
    private lateinit var btnAccept: Button
    private var privacyNewVersion: String = ""
    private lateinit var switchUserManager: Switch

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consent_privacy)

        privacyNewVersion = intent.getStringExtra("privacyNewVersion").safeValue()

        btnAccept = findViewById(R.id.button_save)
        btnAccept.setOnClickListener(this)
        txtPrivacy = findViewById(R.id.webview)
        switchUserManager = findViewById(R.id.switch_user_manager)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(ShortlistListVM::class.java)

        viewModel.getConsentPrivacy()

        viewModel.consentPrivacy.observe(this, Observer { consentPrivacy ->
            webview.loadData(consentPrivacy.content, "text/html; charset=utf-8", "UTF-8")
        })

        viewModel.successUpdate.observe(this, Observer { successUpdate ->
            if (successUpdate) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_save -> {
                submitFeedback()
            }
        }
    }

    private fun submitFeedback() {
        val body: JsonObject = JsonObject()
        body.addProperty("type", "PRIVACY")
        body.addProperty("version", privacyNewVersion)
        if (switchUserManager.isChecked) {
            body.addProperty("meta", "{\\\"accept_marketing\\\":true}")
        } else {
            body.addProperty("meta", "{\\\"accept_marketing\\\":false}")
        }

        viewModel.updateConsent(RequestBody.create(MediaType.parse("application/json"), body.toString()))
    }
}