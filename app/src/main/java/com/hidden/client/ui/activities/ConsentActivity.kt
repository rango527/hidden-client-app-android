package com.hidden.client.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.ShortlistListVM
import kotlinx.android.synthetic.main.activity_privacy_statement.*
import okhttp3.MediaType
import okhttp3.RequestBody

class ConsentActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel: ShortlistListVM
    private lateinit var txtPrivacy: WebView
    private lateinit var btnAccept: Button
    private var termsNewVersion: String = ""
    private var privacyNewVersion: String = ""
    private lateinit var switchUserManager: Switch

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consent_terms)

        termsNewVersion = intent.getStringExtra("termsNewVersion").safeValue()
        privacyNewVersion = intent.getStringExtra("privacyNewVersion").safeValue()

        btnAccept = findViewById(R.id.button_save)
        btnAccept.setOnClickListener(this)
        txtPrivacy = findViewById(R.id.webview)
        switchUserManager = findViewById(R.id.switch_user_manager)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(ShortlistListVM::class.java)

        viewModel.getConsentTerms()

        viewModel.consentTerms.observe(this, Observer { consentTerms ->
            webview.loadData(consentTerms.content, "text/html; charset=utf-8", "UTF-8")
        })

        switchUserManager.setOnCheckedChangeListener { buttonView, isChecked -> viewModel.isChecked = isChecked }

        viewModel.isFormValid.observe(this, Observer { valid ->
            btnAccept.isEnabled = valid ?: true
        })

        viewModel.successUpdate.observe(this, Observer { successUpdate ->
            if (successUpdate) {
                val intent = Intent(applicationContext, ConsentPrivacyActivity::class.java)
                intent.putExtra("privacyNewVersion", privacyNewVersion)

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
        body.addProperty("type", "TERMS")
        body.addProperty("version", termsNewVersion)
        body.addProperty("meta", "{\\\"accept_marketing\\\":true}")

        viewModel.updateConsent(RequestBody.create(MediaType.parse("application/json"), body.toString()))
    }
}