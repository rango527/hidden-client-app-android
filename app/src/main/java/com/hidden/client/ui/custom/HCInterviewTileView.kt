package com.hidden.client.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.hidden.client.R
import com.hidden.client.apis.RetrofitClient
import com.hidden.client.datamodels.HCDashboardResponse
import com.hidden.client.datamodels.HCUpcomingInterviewResponse
import com.hidden.client.helpers.HCGlobal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCInterviewTileView : LinearLayout {

    constructor(context: Context, data: HCDashboardResponse? = null) : super(context) {
        inflate(context, R.layout.dashboard_tile_datetimelocation_view, this)
        if (data != null) {

            val txtUpcominngInterview: TextView = findViewById(R.id.text_upcomingInterview);
            txtUpcominngInterview.setText(data.title)

            val txtUpcomingIntervieCount: TextView = findViewById(R.id.text_upcoming_interview_count)
            val imgStatusIcon: ImageView = findViewById(R.id.image_status_icon)

            RetrofitClient.instance.getUpcomingInterview(data.url, HCGlobal.getInstance().myInfo.getBearerToken())
                .enqueue(object: Callback<List<HCUpcomingInterviewResponse>> {
                    override fun onFailure(call: Call<List<HCUpcomingInterviewResponse>>, t: Throwable) {
                        Toast.makeText(context, "Failed...", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<List<HCUpcomingInterviewResponse>>,
                        response: Response<List<HCUpcomingInterviewResponse>>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()!!.isEmpty()) {
                                txtUpcomingIntervieCount.setText(data.empty_status)
                                imgStatusIcon.setImageResource(R.drawable.empty_upcoming_interviews)
                            } else {

                            }
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }
    }

}