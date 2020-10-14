package com.hidden.client.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.hidden.client.apis.ConversationApi
import com.hidden.client.ui.fileupload.UploadRequestBody
import com.hidden.client.ui.fileupload.getFileName
import kotlinx.android.synthetic.main.activity_video_player.*
import kotlinx.android.synthetic.main.fragment_home_message.*
import okhttp3.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ConversationFileAttachActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {

    private var selectedImage: Uri? = null

    private var conversationId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conversationId = intent.getIntExtra("conversationId", 0)
        openImageChooser()
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_CODE_IMAGE_PICKER -> {
                    selectedImage = data?.data
//                    file_attachment.setImageURI(selectedImage)

                }
            }
        }

        if (selectedImage == null) {
            return
        }

        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImage!!, "r", null) ?:return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedImage!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

//        progressbar.progress  = 0
//        val body = UploadRequestBody(file, "image", this)

//        ConversationApi().uploadImage(
//
//            MultipartBody.Part.createFormData("attachFile", file.name, body),
//            RequestBody.create(MediaType.parse("multipart/form-data"), "Image From My Device")
//        ).enqueue(object: Callback<UploadResponse>{
//            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
//
//            }
//
//            override fun onResponse(
//                call: Call<UploadResponse>,
//                response: Response<UploadResponse>
//            ) {
//                progressbar.progress = 100
//            }
//
//        })
    }

    override fun onProgressUpdate(percentage: Int) {
        progressbar.progress = percentage
    }

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }
}