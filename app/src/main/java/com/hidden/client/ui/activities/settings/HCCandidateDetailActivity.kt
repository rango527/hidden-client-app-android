package com.hidden.client.ui.activities.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.apis.RetrofitClient
import com.hidden.client.datamodels.HCCandidateDetailResponse
import com.hidden.client.datamodels.HCCandidateResponse
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.HCCandidate
import com.hidden.client.ui.HCBaseActivity
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCCandidateDetailActivity : HCBaseActivity() {

    private lateinit var imgPhoto: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_detail)

        initCloseButton()

        val categoryId = intent.getStringExtra("category_id")

        imgPhoto = findViewById(R.id.image_photo)

        RetrofitClient.instance.getCandidateDetail(HCGlobal.getInstance(this).g_client.getBearerToken(), categoryId)
            .enqueue(object: Callback<HCCandidateDetailResponse> {
                override fun onFailure(call: Call<HCCandidateDetailResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<HCCandidateDetailResponse>,
                    response: Response<HCCandidateDetailResponse>
                ) {

                    if (response.isSuccessful) {
                        val photoUrl = response.body()!!.asset__cloudinary_url
                        Glide.with(this@HCCandidateDetailActivity).load(photoUrl).into(imgPhoto)
                    } else {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })

    }
}
