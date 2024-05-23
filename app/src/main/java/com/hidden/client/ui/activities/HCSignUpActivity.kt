package com.hidden.client.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.ui.activities.settings.HCPrivacyStatementActivity
import com.hidden.client.ui.activities.settings.HCTermsOfServiceActivity
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.intro.SignUpVM
import com.kaopiz.kprogresshud.KProgressHUD

class HCSignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var progressDlg: KProgressHUD
    private lateinit var viewModel: SignUpVM
    private var meta: String = ""
    private var isCheckedTerms: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val email: String = intent.getStringExtra("email").safeValue()
        val password: String = intent.getStringExtra("password").safeValue()
        val code: String = intent.getStringExtra("code").safeValue()

        // KProgressHUD
        progressDlg = HCDialog.KProgressDialog(this)

        // Init ViewModel
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(SignUpVM::class.java)
        HCGlobal.getInstance().currentActivity = this

        val closeButton = findViewById<ImageButton>(R.id.image_close)
        closeButton.setOnClickListener(this)

        val switchTerms = findViewById<Switch>(R.id.switch_terms)
        switchTerms.setOnCheckedChangeListener {_, isChecked ->
            isCheckedTerms = isChecked
        }

        val switchService = findViewById<Switch>(R.id.switch_service)
        switchService.setOnCheckedChangeListener {_, isChecked ->
            meta = if (isChecked)
                "{\"accept_marketing\":true}"
            else
                "{\"accept_marketing\":false}"
        }

        val textTerms = findViewById<TextView>(R.id.text_terms)
        textTerms.text = Html.fromHtml(getString(R.string.read_terms_of_service))
        textTerms.setOnClickListener {
            val intent = Intent(this, HCTermsOfServiceActivity::class.java)
            startActivity(intent)
        }

        val textService = findViewById<TextView>(R.id.text_service)
        textService.text = Html.fromHtml(getString(R.string.read_privacy_policy))
        textService.setOnClickListener {
            val intent = Intent(this, HCPrivacyStatementActivity::class.java)
            startActivity(intent)
        }

        val buttonDone = findViewById<Button>(R.id.button_done)
        buttonDone.setOnClickListener{
            if (isCheckedTerms) {
                viewModel.signUp(email, password, code, meta)
            } else {
                HToast.show(HCGlobal.getInstance().currentActivity, "Please accept the Terms", HToast.TOAST_ERROR)
            }
        }

        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            }
            else {
                progressDlg.dismiss()
            }
        })

        // Observing for jumping HomeActivity after SignUp success
        viewModel.navigateToHome.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.image_close -> {
                finish()
            }
        }
    }
}
