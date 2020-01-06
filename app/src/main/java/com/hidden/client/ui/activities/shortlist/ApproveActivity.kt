package com.hidden.client.ui.activities.shortlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.hidden.client.R
import com.hidden.client.datamodels.HCResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApproveActivity : BaseActivity(), View.OnClickListener {

    private lateinit var layoutApprove: LinearLayout

    private lateinit var txtFeedback: TextView
    private lateinit var txtFeedback2: TextView

    private var processId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve)

        initCloseButton()

        processId = intent.getIntExtra("processId", 0);

        txtFeedback = findViewById(R.id.text_feedback_notice)
        txtFeedback2 = findViewById(R.id.text_feedback_notice2)

        layoutApprove = findViewById(R.id.layout_approve)
        layoutApprove.setOnClickListener(this)

        val avatarName = intent.getStringExtra("avatarName").safeValue()
        txtFeedback.text = getString(R.string.feedback_notice, avatarName)
        txtFeedback2.text = getString(R.string.feedback_notice2, avatarName)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.layout_approve -> {
                // Approve

                RetrofitClient.instance.approveShortlist(AppPreferences.apiAccessToken, processId)
                    .enqueue(object: Callback<HCResponse> {
                        override fun onFailure(call: Call<HCResponse>, t: Throwable) {
                            Toast.makeText(this@ApproveActivity, "Failed...", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<HCResponse>,
                            response: Response<HCResponse>
                        ) {
                            if (response.isSuccessful) {
                                val intent = Intent(this@ApproveActivity, HomeActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@ApproveActivity, "Error", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            }
        }
    }
}
