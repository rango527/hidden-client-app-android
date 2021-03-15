package com.hidden.client.ui.activities.settings

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hidden.client.R
import com.hidden.client.datamodels.HCProfileResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.dialogs.HToast
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.intro.EditProfileVM
import com.kaopiz.kprogresshud.KProgressHUD
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class HCMyProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var viewModel: EditProfileVM
    private lateinit var progressDlg: KProgressHUD

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editTitle: EditText

    private lateinit var photo: MultipartBody.Part
    private var changedPhoto: Boolean = false
    private lateinit var imgPhoto: CircleImageView
    private lateinit var btnChoosePhoto: Button

    private lateinit var btnSave: Button

    private var mCurrentPhotoPath: String = ""
    private val PERMISSION_REQUEST_CODE: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        initCloseButton()

        editFirstName = findViewById(R.id.edit_first_name)
        editLastName = findViewById(R.id.edit_last_name)
        editEmail = findViewById(R.id.edit_email)
        editTitle = findViewById(R.id.edit_title)

        imgPhoto = findViewById(R.id.image_photo)

        btnChoosePhoto = findViewById(R.id.button_choose_photo)
        btnChoosePhoto.setOnClickListener(this)

        btnSave = findViewById(R.id.button_save)
        btnSave.setOnClickListener(this)

        RetrofitClient.instance.getProfile(AppPreferences.apiAccessToken)
            .enqueue(object : Callback<HCProfileResponse> {
                override fun onFailure(call: Call<HCProfileResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<HCProfileResponse>,
                    response: Response<HCProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        editFirstName.setText(response.body()!!.client__full_name.substringBefore(" "))
                        editLastName.setText(response.body()!!.client__full_name.substringAfter(" "))
                        editEmail.setText(response.body()!!.client__email)
                        editTitle.setText(response.body()!!.client__job_title)

                        Glide.with(HCGlobal.getInstance().currentActivity)
                            .load(response.body()!!.asset_client__cloudinary_url).into(imgPhoto)
                    } else {
                        HToast.show(
                            HCGlobal.getInstance().currentActivity,
                            "Error",
                            HToast.TOAST_ERROR
                        )
                    }
                }
            })

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(EditProfileVM::class.java)
        HCGlobal.getInstance().currentActivity = this

        editFirstName.doAfterTextChanged { text -> viewModel.firstName = text }
        editLastName.doAfterTextChanged { text -> viewModel.lastName = text }
        editEmail.doAfterTextChanged { text -> viewModel.email = text }
        editTitle.doAfterTextChanged { text -> viewModel.title = text }

        viewModel.isFormValid.observe(this, Observer { valid ->
            btnSave.isEnabled = valid ?: false
        })

        progressDlg = HCDialog.KProgressDialog(this)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show)
                progressDlg.show()
            else
                progressDlg.dismiss()
        })

        viewModel.navigateSetting.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                initCloseButton()
                finish()
                HToast.show(
                    HCGlobal.getInstance().currentActivity,
                    "Details Saved!",
                    HToast.TOAST_SUCCESS
                )
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.button_choose_photo -> {
                val dialog = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.photo_picker_bottom_sheet, null)

                val layoutClose = view.findViewById<LinearLayout>(R.id.layout_cancel)
                layoutClose.setOnClickListener {
                    dialog.dismiss()
                }

                val layoutTakePhoto = view.findViewById<LinearLayout>(R.id.layout_take_photo)
                layoutTakePhoto.setOnClickListener {
                    if (checkPermission()) {
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
                        startActivityForResult(intent, 0)
                        dialog.dismiss()
                    } else {
                        requestPermission()
                    }
                }

                val layoutExistingPhoto =
                    view.findViewById<LinearLayout>(R.id.layout_existing_photo)
                layoutExistingPhoto.setOnClickListener() {
                    if (checkPermission()) {
                        val pickPhoto =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        startActivityForResult(pickPhoto, 1)
                        dialog.dismiss()
                    } else {
                        requestPermission()
                    }
                }

                dialog.setCancelable(false)
                dialog.setContentView(view)
                dialog.show()
            }
            R.id.button_save -> {
                if (changedPhoto) {
                    viewModel.updateProfileImage(photo)
                } else {
                    viewModel.updateProfile()
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(HCGlobal.getInstance().currentActivity, arrayOf(
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE,
            CAMERA
        ), PERMISSION_REQUEST_CODE)
    }

    @SuppressLint("Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == RESULT_OK && data != null) {
//                    val selectedImage = data.extras!!["data"] as Bitmap?
//                    imgPhoto.setImageBitmap(selectedImage)
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor: Cursor? = contentResolver.query(
                        Uri.parse(mCurrentPhotoPath),
                        filePathColumn, null, null, null
                    )
                    if (cursor != null) {
                        cursor.moveToFirst()
                        val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                        val picturePath: String = cursor.getString(columnIndex)

                        val requestBody =
                            RequestBody.create(MediaType.parse("multipart/form-data"), File(picturePath))

                        photo = MultipartBody.Part.createFormData(
                            "photo",
                            File(picturePath).name,
                            requestBody
                        )
                        changedPhoto = true

                        imgPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                        cursor.close()
                    }
                }
                1 -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage: Uri? = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (selectedImage != null) {
                        val cursor: Cursor? = contentResolver.query(
                            selectedImage,
                            filePathColumn, null, null, null
                        )
                        if (cursor != null) {
                            cursor.moveToFirst()
                            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                            val picturePath: String = cursor.getString(columnIndex)

                            val requestBody =
                                RequestBody.create(MediaType.parse("multipart/form-data"), File(picturePath))

                            photo = MultipartBody.Part.createFormData(
                                "photo",
                                File(picturePath).name,
                                requestBody
                            )
                            changedPhoto = true

                            imgPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                            cursor.close()
                        }
                    }
                }
            }
        }
    }
}
