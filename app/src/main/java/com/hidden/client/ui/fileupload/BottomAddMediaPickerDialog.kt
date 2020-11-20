package com.hidden.client.ui.fileupload

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.hidden.client.R
import com.hidden.client.apis.ConversationApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.ConversationFileAttachActivity
import com.hidden.client.ui.activities.ConversationTakePhotoActivity
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class BottomAddMediaPickerDialog(private val conversationId: Int) : DialogFragment() {

    private lateinit var txtTakeVideo: TextView
    private lateinit var txtTakePhoto: TextView
    private lateinit var txtChooseVideo: TextView
    private lateinit var txtChoosePhoto: TextView
    private lateinit var txtCancel: TextView

    private lateinit var layoutBlack: LinearLayout
    private lateinit var attachment: MultipartBody.Part
    private var mCurrentPhotoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.AppTheme_FullScreenDialog
        )
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.AppTheme_Slide)
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.bottom_sheet_media_picker, container, false)

        txtCancel = view.findViewById(R.id.text_cancel)
        txtTakePhoto = view.findViewById(R.id.text_take_photo)
        txtTakeVideo = view.findViewById(R.id.text_take_video)
        txtChooseVideo = view.findViewById(R.id.text_choose_video)
        txtChoosePhoto = view.findViewById(R.id.text_choose_photo)
        layoutBlack = view.findViewById(R.id.layout_blank)

        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        txtCancel.setOnClickListener {
            dismiss()
        }

        txtTakePhoto.setOnClickListener {
//            val intent = Intent(HCGlobal.getInstance().currentActivity, ConversationTakePhotoActivity::class.java)
//            intent.putExtra("conversationId", conversationId)
//            intent.putExtra("requestCode", "TAKE_PHOTO")
//            HCGlobal.getInstance().currentActivity.startActivity(intent)
//
//            dismiss()

//            val values = ContentValues(1)
//            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
//
//            val fileUri: Uri? =
//                contentResolver
//                    .insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        values
//                    )
//
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (intent.resolveActivity(packageManager) != null) {
//                mCurrentPhotoPath = fileUri.toString()
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
//                intent.addFlags(
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION
//                            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//                )
//                startActivityForResult(intent, TAKE_PHOTO_CODE)
//            } else {
//                Toast.makeText(
//                    context,
//                    "SORRY, MEDIA PICKER ERROR...",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }

        }

        txtTakeVideo.setOnClickListener {
//            val intent = Intent(HCGlobal.getInstance().currentActivity, ConversationTakePhotoActivity::class.java)
//            intent.putExtra("conversationId", conversationId)
//            intent.putExtra("requestCode", "TAKE_VIDEO")
//            HCGlobal.getInstance().currentActivity.startActivity(intent)
//
//            dismiss()

//            val values = ContentValues(1)
//            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
//            val fileUri = contentResolver.insert(
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                values
//            )
//            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
//            if (intent.resolveActivity(packageManager) != null) {
//                mCurrentPhotoPath = fileUri.toString()
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
//                intent.addFlags(
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION
//                            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//                )
//                startActivityForResult(intent, TAKE_PHOTO_CODE)
//            } else {
//                Toast.makeText(
//                    context,
//                    "SORRY, MEDIA PICKER ERROR...",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
        }

        txtChoosePhoto.setOnClickListener {
            if (checkFileAttachPermission()) {
                val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                startActivityForResult(intent, IMAGE_PICK_CODE)
            } else {
                requestFileAttachPermission()
            }
        }

        txtChooseVideo.setOnClickListener {
            if (checkFileAttachPermission()) {
                val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                intent.type = "video/*"
                startActivityForResult(intent, IMAGE_PICK_CODE)
            } else {
                requestFileAttachPermission()
            }
        }

        layoutBlack.setOnClickListener {
            dismiss()
        }
    }

    private fun checkFileAttachPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(HCGlobal.getInstance().currentActivity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestFileAttachPermission() {
        ActivityCompat.requestPermissions(HCGlobal.getInstance().currentActivity, arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        ),
            PERMISSION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == IMAGE_PICK_CODE ) {
                val fileToUpload = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)
                val requestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), File(fileToUpload))

                attachment = MultipartBody.Part.createFormData(
                    "attachment",
                    File(fileToUpload)?.name,
                    requestBody
                )
            } else if (requestCode == TAKE_PHOTO_CODE) {
//                val cursor = contentResolver.query(
//                    Uri.parse(mCurrentPhotoPath),
//                    Array(1) { android.provider.MediaStore.Images.ImageColumns.DATA },
//                    null, null, null
//                )
//                cursor.moveToFirst()
//                val photoPath = cursor.getString(0)
//                cursor.close()

//                val fileToUpload = photoPath.toString()
//                val requestBody =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), File(fileToUpload))

//                attachment = MultipartBody.Part.createFormData(
//                    "attachment",
//                    File(fileToUpload).name,
//                    requestBody
//                )
            }

            val call = ConversationApi().uploadImage(
                AppPreferences.apiAccessToken,
                conversationId,
                attachment
            )
            call.enqueue(object : Callback<UploadResponse> {

                override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {
                    Toast.makeText(context,"UPLOAD FAILURE", Toast.LENGTH_SHORT).show()
                    Log.d("ONFAILURE",t.toString())
                }

                override fun onResponse(call: Call<UploadResponse>?, response: Response<UploadResponse>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            Toast.makeText(context,"Success file upload", Toast.LENGTH_SHORT).show()
//                            finish()
                        }
                    } else {
                        Toast.makeText(context,"UPLOAD FAILURE", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    companion object {
        private const val TAG = "bottom_add_media_picker_dialog"
        private const val IMAGE_PICK_CODE = 1000
        private const val TAKE_PHOTO_CODE = 2000
        private val CAPTURE_FROM_GALLEY = 1
        private val CAPTURE_FROM_CAMERA = 2

        private val PERMISSION_REQUEST_CODE: Int = 101
        fun display(fragmentManager: FragmentManager?, conversationId: Int): BottomAddMediaPickerDialog {
            val dialog = BottomAddMediaPickerDialog(conversationId)
            dialog.show(fragmentManager!!, TAG)
            return dialog
        }
    }
}