package com.hidden.client.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.hidden.client.R
import com.hidden.client.apis.ConversationApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.fileupload.UploadRequestBody
import com.hidden.client.ui.fileupload.UploadResponse
import com.hidden.client.ui.fragments.process.HCMessageFragment
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import kotlinx.android.synthetic.main.activity_video_player.*
import okhttp3.*
import java.io.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class ConversationFileAttachActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private var conversationId: Int = 0
    private val PERMISSION_REQUEST_CODE: Int = 101
    private lateinit var attachment: MultipartBody.Part

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conversationId = intent.getIntExtra("conversationId", 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(HCGlobal.getInstance().currentActivity, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ), PERMISSION_REQUEST_CODE)
            } else {
                openImageChooser()
            }
        } else {
            openImageChooser()
        }
    }

    private fun openImageChooser() {
        MaterialFilePicker()
            .withActivity(this)
            .withRequestCode(IMAGE_PICK_CODE)
            .withFilter(Pattern.compile("^.*"))
            .withHiddenFiles(true)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            val fileToUpload = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), File(fileToUpload))

            attachment = MultipartBody.Part.createFormData("attachment", File(fileToUpload)?.name,requestBody)

            val call = ConversationApi().uploadImage(
                AppPreferences.apiAccessToken,
                conversationId,
                attachment
            )
            call.enqueue(object : Callback<UploadResponse> {
                override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {
                    HToast.show(HCGlobal.getInstance().currentActivity, "Upload Failure!", HToast.TOAST_ERROR)
                    Log.d("ONFAILURE",t.toString())
                }

                override fun onResponse(call: Call<UploadResponse>?, response: Response<UploadResponse>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            finish()
//                            onBackPressed()
//                            onBackPressed()
                        }
                    } else {
                        HToast.show(HCGlobal.getInstance().currentActivity, "Upload Failure!", HToast.TOAST_ERROR)
                    }
                }
            })
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        progressbar.progress = percentage
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        const val PERMISSION_CODE = 1001
    }
}

