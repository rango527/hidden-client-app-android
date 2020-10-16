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
import android.content.DialogInterface
//import butterknife.BindView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.facebook.common.util.UriUtil
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.hidden.client.R
import com.hidden.client.apis.ConversationApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.ui.fileupload.UploadRequestBody
import com.hidden.client.ui.fileupload.UploadResponse
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import kotlinx.android.synthetic.main.activity_video_player.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ConversationTakePhototestActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private lateinit var imageView: ImageView
    private lateinit var pickImage: Button
    private lateinit var upload: Button
    private val mMediaUri: Uri? = null

    private var fileUri: Uri? = null

    private var mediaPath: String? = null

    private var mImageFileLocation = ""
    private lateinit var pDialog: ProgressDialog
    private var postPath: String? = null
    val CAMERA_REQUEST_CODE = 0
    private var conversationId: Int = 0
    private val TAKE_PHOTO_REQUEST = 101
    private var mCurrentPhotoPath: String = ""
    lateinit var imageFilePath: String
    private lateinit var attachment: MultipartBody.Part

//
//    @JvmField @BindView(R.id.take_photo)
//    var takePhotoBtn: SimpleDraweeView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conversationId = intent.getIntExtra("conversationId", 0)
        validatePermissions()
    }

    private fun validatePermissions() {
        Dexter.withActivity(this)
            .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    AlertDialog.Builder(this@ConversationTakePhototestActivity)
                        .setTitle("getString(R.string.storage_permission_rationale_title)")
                        .setMessage("getString(R.string.storage_permission_rationale_message)")
                        .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener {
                                dialogInterface, i ->
                            dialogInterface.dismiss()
                            token?.cancelPermissionRequest()
                        })
                        .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener {
                                dialogInterface, i ->
                            dialogInterface.dismiss()
                            token?.continuePermissionRequest()
                        })
                        .show()
                }

                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report?.areAllPermissionsGranted()!!) {

                        try {
                            val imageFile = createImageFile()
                            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            if(callCameraIntent.resolveActivity(packageManager) != null) {
                                val authorities = packageName + ".fileprovider"
                                val imageUri = FileProvider.getUriForFile(this@ConversationTakePhototestActivity, authorities, imageFile)
                                callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                                startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
                            }
                        } catch (e: IOException) {
                            Toast.makeText(this@ConversationTakePhototestActivity, "Could not create file!", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }
            ).check()
    }

    private fun launchCamera() {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        val fileUri = contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null) {
            mCurrentPhotoPath = fileUri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, TAKE_PHOTO_REQUEST)
        }
//        val capture = Intent()
//        capture.action = MediaStore.ACTION_IMAGE_CAPTURE
//        startActivityForResult(capture, TAKE_PHOTO_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PHOTO_REQUEST) {
            val imgUri = Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(imageFilePath)
                .build()
//        val file = File(photoPath)
//        val uri = Uri.fromFile(file)
//
//        val height = resources.getDimensionPixelSize(R.dimen.default_panel_width)
//        val width = resources.getDimensionPixelSize(R.dimen.default_panel_height)

            val fileToUpload = imgUri.toString()
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),File(fileToUpload))

            attachment = MultipartBody.Part.createFormData("attachment",File(fileToUpload)?.name,requestBody)

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
                    if(response != null){
                        if(response.isSuccessful){
                            var message = response.body()?.message

//                            Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()

//                            if(message!!.contains("successfull")){
                            finish()
//                            }
                        }
                    }
                }
            })
//        finish()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName: String = "JPEG_" + timeStamp + "_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if(!storageDir!!.exists()) storageDir?.mkdirs()
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = imageFile.absolutePath
        return imageFile
    }

    override fun onProgressUpdate(percentage: Int) {
        progressbar.progress = percentage
    }
}