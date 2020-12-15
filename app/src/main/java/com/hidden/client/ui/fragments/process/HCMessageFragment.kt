package com.hidden.client.ui.fragments.process

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hidden.client.R
import com.hidden.client.apis.ConversationApi
import com.hidden.client.databinding.MessageListBinding
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.doAfterTextChanged
import com.hidden.client.ui.activities.ConversationFileAttachActivity
import com.hidden.client.ui.activities.shortlist.InterviewActivity
import com.hidden.client.ui.fileupload.BottomAddMediaPickerDialog
import com.hidden.client.ui.fileupload.UploadResponse
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.MessageListVM
import com.kaopiz.kprogresshud.KProgressHUD
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import kotlinx.android.synthetic.main.fragment_home_message.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.regex.Pattern
import kotlin.math.roundToInt


class HCMessageFragment(
    private val conversationId: Int
) : Fragment(), View.OnClickListener {

    private lateinit var binding: MessageListBinding
    private lateinit var viewModel: MessageListVM

    private lateinit var messageSendBtn: Button
    private lateinit var attachFileBtn: ImageView
    private lateinit var takePhotoBtn: ImageView
    private lateinit var scrollView: ScrollView
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutSendMessage: LinearLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var imageView: ImageView
    private lateinit var textMessage: EditText
    private lateinit var layoutFeat: LinearLayout

    private val CAPTURE_REQUEST_CODE = 42
    private lateinit var attachment: MultipartBody.Part
    private lateinit var progressDlg: KProgressHUD

    private val CAPTURE_FROM_GALLEY = 1
    private val PERMISSION_REQUEST_CODE: Int = 101

    private var mediaPath: String? = null
    private var postPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, ViewModelFactory(context!!)).get(MessageListVM::class.java)

        viewModel.conversationId = conversationId
        viewModel.loadMessage(false, conversationId)

        progressDlg = HCDialog.KProgressDialog(context!!)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            } else {
                progressDlg.dismiss()
                swipeRefreshLayout.isRefreshing = false
            }
        })

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = MessageListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        val view = binding.root

        recyclerView = view.findViewById(R.id.recyclerview_messages)
//        recyclerView.smoothScrollToPosition(HCGlobal.getInstance().currentMessageCount - 1)
//        recyclerView.scrollToPosition(HCGlobal.getInstance().currentMessageCount - 1)
//
        viewModel.loadingMessageList.observe(this, Observer { show ->
            if (show) {
                recyclerView.smoothScrollToPosition(HCGlobal.getInstance().currentMessageCount )
            }
        })

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        val density: Float = resources.displayMetrics.density

        val height = displayMetrics.heightPixels

        swipeRefreshLayout = view.findViewById(R.id.swipeContainerMessageList)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.loadMessage(false, conversationId)
        }
        layoutSendMessage = view.findViewById(R.id.layout_send_message)
        textMessage = view.findViewById(R.id.edit_text_message)
//        layoutFeat = view.findViewById(R.id.layout_feat)

        textMessage.measure(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val textSendMessageHeight = textMessage.measuredHeight

        layoutSendMessage.measure(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        var layoutSendMessageHeight = layoutSendMessage.measuredHeight

        binding.recyclerviewMessages.layoutManager = LinearLayoutManager(context!!)

        // get message list - scrollview height
//        recyclerView.layoutParams.height = height - layoutSendMessageHeight - (140 * density).roundToInt()
        swipeRefreshLayout.layoutParams.height = height - layoutSendMessageHeight - (140 * density).roundToInt()

        textMessage.doAfterTextChanged {
//                text -> viewModel.resetEmail = text
            layoutSendMessage.measure(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutSendMessageHeight = layoutSendMessage.measuredHeight
            swipeRefreshLayout.layoutParams.height = height - layoutSendMessageHeight - (140 * density).roundToInt()
            recyclerView.smoothScrollToPosition(HCGlobal.getInstance().currentMessageCount - 1)
        }

        messageSendBtn = view.findViewById(R.id.message_send_button)
        messageSendBtn.setOnClickListener(this)

        attachFileBtn = view.findViewById(R.id.file_attachment)
        attachFileBtn.setOnClickListener(this)

        takePhotoBtn = view.findViewById(R.id.take_photo)
        takePhotoBtn.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.message_send_button -> {
                val message = edit_text_message.text.toString()

                if (message != "") {
                    viewModel.sendMessage(conversationId, message)
                    edit_text_message.setText("")
                }
            }

            R.id.file_attachment -> {
                if (checkPermission()) {
//                    val intent = Intent(Intent.ACTION_PICK)
//                    startActivityForResult(intent, CAPTURE_FROM_GALLEY)
                    MaterialFilePicker()
                        .withActivity(HCGlobal.getInstance().currentActivity)
                        .withRequestCode(CAPTURE_FROM_GALLEY)
                        .withFilter(Pattern.compile("^.*"))
                        .withHiddenFiles(true)
                        .start()
                } else {
                    requestPermission()
                }
//                val intent = Intent(HCGlobal.getInstance().currentActivity, ConversationFileAttachActivity::class.java)
//
//                intent.putExtra("conversationId", conversationId)
//                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }

            R.id.take_photo -> {
                BottomAddMediaPickerDialog.display(
                    activity!!.supportFragmentManager,
                    conversationId
                )
            }
        }
    }

    private fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(HCGlobal.getInstance().currentActivity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ),
            PERMISSION_REQUEST_CODE)
    }

    @SuppressLint("Recycle", "Assert")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CAPTURE_FROM_GALLEY && data != null) {
////            val selectedImage = data.data
//////            val filePathColumn = arrayOf(FilePickerActivity.RESULT_FILE_PATH)
////            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
////
////            val cursor = resolver.query(selectedImage!!, filePathColumn, null, null, null)
////            assert(cursor != null)
////            cursor!!.moveToFirst()
////
////            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
////            mediaPath = cursor.getString(columnIndex)
////            cursor.close()
////            postPath = mediaPath

//            val fileToUpload = data.data?.path
            val fileToUpload = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)!!.toString()
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), File(fileToUpload))

            attachment = MultipartBody.Part.createFormData("attachment", File(fileToUpload)?.name, requestBody)

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
    //                        finish()
                        } else {
                            Toast.makeText(context,"UPLOAD FAILURE", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context,"UPLOAD FAILURE", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}