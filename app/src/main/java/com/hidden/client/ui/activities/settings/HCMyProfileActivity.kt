package com.hidden.client.ui.activities.settings

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hidden.client.R
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.datamodels.HCProfileResponse
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.BaseActivity
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCMyProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editTitle: EditText

    private lateinit var imgPhoto: CircleImageView
    private lateinit var btnChoosePhoto: Button

    private lateinit var btnSave: Button

    private val CAPTURE_FROM_GALLEY = 1
    private val CAPTURE_FROM_CAMERA = 2

    private val PERMISSION_REQUEST_CODE: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        initCloseButton()

        editName = findViewById(R.id.edit_name)
        editEmail = findViewById(R.id.edit_email)
        editTitle = findViewById(R.id.edit_title)

        imgPhoto = findViewById(R.id.image_photo)

        btnChoosePhoto = findViewById(R.id.button_choose_photo)
        btnChoosePhoto.setOnClickListener(this)

        btnSave = findViewById(R.id.button_save)
        btnSave.setOnClickListener(this)

        var currentPhotoPath: String;

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
        when(v!!.id) {
            R.id.button_choose_photo -> {
                val dialog = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.photo_picker_bottom_sheet, null)

                val layoutClose = view.findViewById<LinearLayout>(R.id.layout_cancel)
                layoutClose.setOnClickListener(View.OnClickListener {
                    dialog.dismiss();
                })

                var layoutTakePhoto = view.findViewById<LinearLayout>(R.id.layout_take_photo)
                layoutTakePhoto.setOnClickListener(View.OnClickListener {
                    if (checkPersmission()) {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, CAPTURE_FROM_CAMERA)
                    } else {
                        requestPermission()
                    }

                })

                dialog.setCancelable(false)
                dialog.setContentView(view)
                dialog.show()
            }
            R.id.button_save -> {

            }
        }
    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA),
            PERMISSION_REQUEST_CODE)
    }

//    @Throws(IOException::class)
//    private fun createFile(): File {
        // Create an image file name
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: File = getExternalFilesDir(Environment!!.DIRECTORY_PICTURES)
//        return File.createTempFile(
//            "JPEG_${timeStamp}_", /* prefix */
//            ".jpg", /* suffix */
//            storageDir /* directory */
//        ).apply {
//            // Save a file: path for use with ACTION_VIEW intents
//            currentPhoto = absolutePath
//        }
//    }

}
