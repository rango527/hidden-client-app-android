package com.hidden.client.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hidden.client.apis.ConversationApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.ui.fileupload.UploadRequestBody
import com.hidden.client.ui.fileupload.UploadResponse
import com.hidden.client.ui.fileupload.getFileName
import pub.devrel.easypermissions.EasyPermissions
import com.hidden.client.ui.viewmodels.main.MessageListVM
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import kotlinx.android.synthetic.main.activity_video_player.*
import kotlinx.android.synthetic.main.fragment_home_message.*
import okhttp3.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassesTracker

class ConversationFileAttachActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private var conversationId: Int = 0
    private var requestCode: String? = ""

    private lateinit var attachment: MultipartBody.Part

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conversationId = intent.getIntExtra("conversationId", 0)
        requestCode = intent.getStringExtra("requestCode")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                openImageChooser()
            }
        } else {
            openImageChooser()
        }
    }

    private fun openImageChooser() {
//        if (!EasyPermissions.hasPermissions(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            EasyPermissions.requestPermissions(this,"Application need your permission for accessing the Storage",991,android.Manifest.permission.READ_EXTERNAL_STORAGE)
//            EasyPermissions.requestPermissions(this,"Application need your permission for accessing the Storage",992,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        }

//        if(EasyPermissions.hasPermissions(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            when (requestCode) {
                "CHOOSE_PHOTO" -> {
//                    MaterialFilePicker()
//                        .withActivity(this)
//                        .withRequestCode(IMAGE_PICK_CODE)
//                        .withFilter(Pattern.compile("^.*.(jpg|jpeg|png)$"))
//                        .withHiddenFiles(true)
//                        .start()
                    val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intent.type = "image/*"
                    startActivityForResult(intent, IMAGE_PICK_CODE)
                }
                "CHOOSE_VIDEO" -> {
//                    MaterialFilePicker()
//                        .withActivity(this)
//                        .withRequestCode(IMAGE_PICK_CODE)
//                        .withFilter(Pattern.compile("^.*.(mp4|avi|3gp|wmv|flv)$"))
//                        .withHiddenFiles(true)
//                        .start()
                    val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                    intent.type = "video/*"
                    startActivityForResult(intent, IMAGE_PICK_CODE)
                }
                else -> {
                    MaterialFilePicker()
                        .withActivity(this)
                        .withRequestCode(IMAGE_PICK_CODE)
                        .withFilter(Pattern.compile("^.*"))
                        .withHiddenFiles(true)
                        .start()
                }
            }
//        } else {
//            EasyPermissions.requestPermissions(this,"Application need your permission for accessing the Storage",991,android.Manifest.permission.READ_EXTERNAL_STORAGE)
//            EasyPermissions.requestPermissions(this,"Application need your permission for accessing the Storage",992,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        }


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
            call.enqueue(object : Callback<UploadResponse>{

                override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {
                    Toast.makeText(applicationContext,"CONNECTION FAILURE",Toast.LENGTH_SHORT).show()
                    Log.d("ONFAILURE",t.toString())
                }

                override fun onResponse(call: Call<UploadResponse>?, response: Response<UploadResponse>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            finish()
                        }
                    } else {
                        Toast.makeText(applicationContext,"CONNECTION FAILURE",Toast.LENGTH_SHORT).show()
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

