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
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RejectActivity : BaseActivity(), View.OnClickListener {

    private lateinit var layoutReject: LinearLayout

    private lateinit var txtFeedback: TextView
    private lateinit var txtFeedback2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reject)

        initCloseButton()

        txtFeedback = findViewById(R.id.text_feedback_notice)
        txtFeedback2 = findViewById(R.id.text_feedback_notice2)

        layoutReject = findViewById(R.id.layout_reject)
        layoutReject.setOnClickListener(this)

        txtFeedback.text = getString(R.string.feedback_notice3, HCGlobal.getInstance().currentShortlist[HCGlobal.getInstance().currentIndex].avatar__name)
        txtFeedback2.text = getString(R.string.feedback_notice2, HCGlobal.getInstance().currentShortlist[HCGlobal.getInstance().currentIndex].avatar__name)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.layout_reject -> {
                // Approve

                val processId: Int = HCGlobal.getInstance().currentShortlist[HCGlobal.getInstance().currentIndex].process__process_id

                RetrofitClient.instance.rejectShortlist(AppPreferences.apiAccessToken, processId)
                    .enqueue(object: Callback<HCResponse> {
                        override fun onFailure(call: Call<HCResponse>, t: Throwable) {
                            Toast.makeText(this@RejectActivity, "Failed...", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<HCResponse>,
                            response: Response<HCResponse>
                        ) {
                            if (response.isSuccessful) {
                                val intent = Intent(this@RejectActivity, HomeActivity::class.java)
                                intent.putExtra("saveIndex", true)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@RejectActivity, "Error", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            }
        }
    }
}
