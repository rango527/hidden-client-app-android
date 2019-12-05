package com.hidden.client.ui.activities.shortlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import com.hidden.client.R
import com.hidden.client.datamodels.HCResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.activities.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RejectActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var imgBack: ImageButton
    private lateinit var layoutReject: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reject)

        imgBack = findViewById(R.id.button_back)
        imgBack.setOnClickListener(this)

        layoutReject = findViewById(R.id.layout_reject)
        layoutReject.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.button_back -> {
                val intent = Intent(this, ShortlistDetailActivity::class.java)
                startActivity(intent)
                finish()
            }
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
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@RejectActivity, "Error", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ShortlistDetailActivity::class.java)
        startActivity(intent)
        finish()
    }
}
