package com.hidden.client.ui.activities.settings

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.apis.RetrofitClient
import com.hidden.client.datamodels.HCProfileResponse
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.HCBaseActivity
import com.hidden.client.ui.custom.HCRoundedBottomSheetDialogFragment
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCMyProfileActivity : HCBaseActivity(), View.OnClickListener {

    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editTitle: EditText

    private lateinit var imgPhoto: CircleImageView
    private lateinit var btnChoosePhoto: Button

    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        initCloseButton()

        editName = findViewById(R.id.edit_name)
        editEmail = findViewById(R.id.edit_email)
        editTitle = findViewById(R.id.edit_title)

        imgPhoto = findViewById(R.id.image_photo)

        btnChoosePhoto = findViewById(R.id.button_choose_photo)
        btnSave = findViewById(R.id.button_save)

        RetrofitClient.instance.getProfile(HCGlobal.getInstance().myInfo.getBearerToken())
            .enqueue(object: Callback<HCProfileResponse> {
                override fun onFailure(call: Call<HCProfileResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<HCProfileResponse>,
                    response: Response<HCProfileResponse>
                ) {

                    if (response.isSuccessful) {
                        editName.setText(response.body()!!.client__full_name)
                        editEmail.setText(response.body()!!.client__email)
                        editTitle.setText(response.body()!!.client__job_title)

                        Glide.with(this@HCMyProfileActivity)
                            .load(response.body()!!.asset_client__cloudinary_url).into(imgPhoto)

                    } else {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

    override fun onClick(v: View?) {

    }
}
