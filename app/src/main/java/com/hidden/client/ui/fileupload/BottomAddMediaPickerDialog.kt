package com.hidden.client.ui.fileupload

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.hidden.client.R
import com.hidden.client.apis.ConversationApi
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.fragments.process.HCMessageFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class BottomAddMediaPickerDialog(
    private val HCMessageFragment: HCMessageFragment,
    private val conversationId: Int
    ) : DialogFragment() {
    private lateinit var txtTakeVideo: TextView
    private lateinit var txtTakePhoto: TextView
    private lateinit var txtChooseVideo: TextView
    private lateinit var txtChoosePhoto: TextView
    private lateinit var txtCancel: TextView

    private lateinit var layoutBlack: LinearLayout
    private lateinit var attachment: MultipartBody.Part
    private var mCurrentPhotoPath: String = ""

    private val CAPTURE_FROM_GALLEY = 1
    private val CAPTURE_FROM_CAMERA = 2

    private val PERMISSION_REQUEST_CODE: Int = 101

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
            if (checkCameraPermission()) {
                val values = ContentValues(1)
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")

                val fileUri: Uri? =
                    HCGlobal.getInstance().currentActivity.contentResolver
                        .insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values
                        )
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                mCurrentPhotoPath = fileUri.toString()
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                intent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                startActivityForResult(intent, CAPTURE_FROM_CAMERA)
            } else {
                requestCameraPermission()
            }
        }

        txtTakeVideo.setOnClickListener {
            if (checkCameraPermission()) {
                val values = ContentValues(1)
                values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                val fileUri = HCGlobal.getInstance().currentActivity.contentResolver.insert(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    values
                )
                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                mCurrentPhotoPath = fileUri.toString()
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                intent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                startActivityForResult(intent, CAPTURE_FROM_CAMERA)
            } else {
                requestCameraPermission()
            }
        }

        txtChoosePhoto.setOnClickListener {
            if (checkFileAttachPermission()) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                startActivityForResult(intent, CAPTURE_FROM_GALLEY)
            } else {
                requestFileAttachPermission()
            }
        }

        txtChooseVideo.setOnClickListener {
            if (checkFileAttachPermission()) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "video/*"
                startActivityForResult(intent, CAPTURE_FROM_GALLEY)
            } else {
                requestFileAttachPermission()
            }
        }

        layoutBlack.setOnClickListener {
            dismiss()
        }
    }

    private fun checkFileAttachPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(HCGlobal.getInstance().currentActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestFileAttachPermission() {
        ActivityCompat.requestPermissions(HCGlobal.getInstance().currentActivity, arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ), PERMISSION_REQUEST_CODE)
    }

    private fun checkCameraPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(HCGlobal.getInstance().currentActivity, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(HCGlobal.getInstance().currentActivity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(HCGlobal.getInstance().currentActivity, arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ), PERMISSION_REQUEST_CODE)
    }

    @SuppressLint("Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            dismiss()
            if (requestCode == CAPTURE_FROM_GALLEY ) {
                val selectedImage: Uri? = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                if (selectedImage != null) {
                    val cursor = HCGlobal.getInstance().currentActivity.contentResolver.query(
                        selectedImage,
                        filePathColumn, null, null, null
                    )
                    if (cursor != null) {
                        cursor.moveToFirst()
                        val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                        val picturePath: String = cursor.getString(columnIndex)

                        val requestBody =
                            RequestBody.create(MediaType.parse("multipart/form-data"), File(picturePath))

                        attachment = MultipartBody.Part.createFormData(
                            "attachment",
                            File(picturePath).name,
                            requestBody
                        )
                        cursor.close()
                    }
                }
            } else if (requestCode == CAPTURE_FROM_CAMERA) {
                val cursor = HCGlobal.getInstance().currentActivity.contentResolver.query(
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
                    File(fileToUpload).name,
                    requestBody
                )
            }

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
                        HCMessageFragment.onRefreshMessageFragment()
                    } else {
                        HToast.show(HCGlobal.getInstance().currentActivity, "Upload Failure!", HToast.TOAST_ERROR)
                    }
                }
            })
        }
    }

    companion object {
        private const val TAG = "bottom_add_media_picker_dialog"

        fun display(fragmentManager: FragmentManager?,HCMessageFragment:HCMessageFragment, conversationId: Int): BottomAddMediaPickerDialog {
            val dialog = BottomAddMediaPickerDialog(HCMessageFragment, conversationId)
            dialog.show(fragmentManager!!, TAG)
            return dialog
        }
    }
}