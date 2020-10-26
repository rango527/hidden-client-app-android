package com.hidden.client.ui.activities

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
//import butterknife.BindView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.hidden.client.R
import com.hidden.client.apis.ConversationApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.ui.fileupload.UploadRequestBody
import com.hidden.client.ui.fileupload.UploadResponse
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import kotlinx.android.synthetic.main.activity_video_player.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ConversationTakePhotoActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private lateinit var imageView: ImageView
    private lateinit var pickImage: Button
    private lateinit var upload: Button
    private val mMediaUri: Uri? = null

    private var fileUri: Uri? = null

    private var mediaPath: String? = null

    private var mImageFileLocation = ""
    private lateinit var pDialog: ProgressDialog
    private var postPath: String? = null

    private var conversationId: Int = 0
    private var requestCode: String? = ""
    private val TAKE_PHOTO_REQUEST = 101
    private var mCurrentPhotoPath: String = ""

    private lateinit var attachment: MultipartBody.Part

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conversationId = intent.getIntExtra("conversationId", 0)
        requestCode = intent.getStringExtra("requestCode")

//        if (!EasyPermissions.hasPermissions(
//                this@ConversationTakePhotoActivity,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//        ) {EasyPermissions.requestPermissions(
//            this,
//            "Application need your permission for accessing the Storage",
//            991,
//            android.Manifest.permission.READ_EXTERNAL_STORAGE
//        )
//            EasyPermissions.requestPermissions(
//                this,
//                "Application need your permission for accessing the Storage",
//                992,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)}

//        if (!EasyPermissions.hasPermissions(
//                this@ConversationTakePhotoActivity,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) && !EasyPermissions.hasPermissions(
//                this@ConversationTakePhotoActivity,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            )
//        ) {
            when (requestCode) {
                "TAKE_PHOTO" -> {
                    val values = ContentValues(1)
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")

                    val fileUri: Uri? =
                        contentResolver
                            .insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values
                            )

                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (intent.resolveActivity(packageManager) != null) {
                        mCurrentPhotoPath = fileUri.toString()
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                        intent.addFlags(
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                        startActivityForResult(intent, TAKE_PHOTO_REQUEST)
//                    val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    if (photoIntent.resolveActivity(packageManager) != null) {
//                        startActivityForResult(photoIntent)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "SORRY, MEDIA PICKER ERROR...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                "TAKE_VIDEO" -> {
                    val values = ContentValues(1)
                    values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                    val fileUri = contentResolver.insert(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        values
                    )
                    val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                    if (intent.resolveActivity(packageManager) != null) {
                        mCurrentPhotoPath = fileUri.toString()
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                        intent.addFlags(
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                        startActivityForResult(intent, TAKE_PHOTO_REQUEST)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "SORRY, MEDIA PICKER ERROR...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else -> {
                    Toast.makeText(
                        applicationContext,
                        "SORRY, MEDIA PICKER ERROR...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
//        } else {
//            EasyPermissions.requestPermissions(
//                this,
//                "Application need your permission for accessing the Storage",
//                992,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            EasyPermissions.requestPermissions(
//                this,
//                "Application need your permission for accessing the Storage",
//                991,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PHOTO_REQUEST) {

            if (EasyPermissions.hasPermissions(
                    this@ConversationTakePhotoActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                val cursor = contentResolver.query(
                    Uri.parse(mCurrentPhotoPath),
                    Array(1) { android.provider.MediaStore.Images.ImageColumns.DATA },
                    null, null, null
                )
                cursor?.moveToFirst()
                val photoPath = cursor?.getString(0)
                cursor?.close()

                val fileToUpload = photoPath.toString()
                val requestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), File(fileToUpload))

                attachment = MultipartBody.Part.createFormData(
                    "attachment",
                    File(fileToUpload)?.name,
                    requestBody
                )

                val call = ConversationApi().uploadImage(
                    AppPreferences.apiAccessToken,
                    conversationId,
                    attachment
                )
                call.enqueue(object : Callback<UploadResponse> {

                    override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {
                        Toast.makeText(applicationContext, "CONNECTION FAILURE", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("ONFAILURE", t.toString())
                    }

                    override fun onResponse(
                        call: Call<UploadResponse>?,
                        response: Response<UploadResponse>?
                    ) {
                        if (response != null) {
                            if (response.isSuccessful) {
//                                var message = response.body()?.message
                                finish()
                            }
                        }
                    }
                })
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "Application need your permission for accessing the Storage",
                    991,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                EasyPermissions.requestPermissions(
                    this,
                    "Application need your permission for accessing the Storage",
                    992,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            }
//        finish()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this@ConversationTakePhotoActivity)
    }

    override fun onProgressUpdate(percentage: Int) {
        progressbar.progress = percentage
    }
}