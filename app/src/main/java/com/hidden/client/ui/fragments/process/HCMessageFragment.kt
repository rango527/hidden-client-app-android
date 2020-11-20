package com.hidden.client.ui.fragments.process

import android.Manifest
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.apis.ConversationApi
import com.hidden.client.databinding.MessageListBinding
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.fileupload.BottomAddMediaPickerDialog
import com.hidden.client.ui.fileupload.UploadResponse
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.MessageListVM
import com.kaopiz.kprogresshud.KProgressHUD
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

    private lateinit var imageView: ImageView

    private val CAPTURE_REQUEST_CODE = 42
    private lateinit var attachment: MultipartBody.Part
    private lateinit var progressDlg: KProgressHUD

    private val CAPTURE_FROM_CAMERA = 2
    private val PERMISSION_REQUEST_CODE: Int = 101

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
            }
        })

//        viewModel.loadingMessageList.observe(this, Observer { show ->
//            if (show) {
//                scrollView.post(Runnable {
//                    scrollView.scrollTo(0, scrollView.bottom)
//                })
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
//                recyclerview_messages.smoothScrollToPosition(HCGlobal.getInstance().currentMessageCount - 1)
//            } else {
//                scrollView.post(Runnable {
//                    scrollView.scrollTo(0, scrollView.bottom)
//                })
//            }
//        })
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = MessageListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        val view = binding.root

        scrollView = view.findViewById(R.id.scrollview_message)
        recyclerView = view.findViewById(R.id.recyclerview_messages)
//        recyclerView.smoothScrollToPosition(HCGlobal.getInstance().currentMessageCount - 1)
//        recyclerView.scrollToPosition(HCGlobal.getInstance().currentMessageCount - 1)

//        viewModel.loadingMessageList.observe(this, Observer { show ->
//            if (show) {
//                scrollView.post(Runnable {
//                    scrollView.scrollTo(0, scrollView.bottom)
//                })
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
//                recyclerview_messages.smoothScrollToPosition(HCGlobal.getInstance().currentMessageCount - 1)
//            } else {
//                scrollView.post(Runnable {
//                    scrollView.scrollTo(0, scrollView.bottom)
//                })
//            }
//        })


        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        val density: Float = resources.displayMetrics.density

        val height = displayMetrics.heightPixels


        layoutSendMessage = view.findViewById(R.id.layout_send_message)

        layoutSendMessage.measure(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val layoutSendMessageHeight = layoutSendMessage.measuredHeight

        // get message list - scrollview height
        scrollView.layoutParams.height = height - layoutSendMessageHeight - (140 * density).roundToInt()
//        viewModel.loadingMessageList.observe(this, Observer { show ->
//            if (show) {
//                scrollView.post(Runnable {
//                    scrollView.scrollTo(0, scrollView.bottom)
//                })
////                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
////                recyclerview_messages.smoothScrollToPosition(HCGlobal.getInstance().currentMessageCount - 1)
//            } else {
//                scrollView.post(Runnable {
//                    scrollView.scrollTo(0, scrollView.bottom)
//                })
//            }
//        })

        binding.recyclerviewMessages.layoutManager = LinearLayoutManager(context!!)

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
                if (message == "") {
//                    Toast.makeText(this@HCMessageFragment, "Please write a message, first...", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.sendMessage(conversationId, message)
                    edit_text_message.setText("")
                }
            }

            R.id.file_attachment -> {
                if (checkPermission()) {
                    val intent = Intent(Intent.ACTION_PICK)
                    startActivityForResult(intent, IMAGE_PICK_CODE)
                } else {
                    requestPermission()
                }
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
        ActivityCompat.requestPermissions(HCGlobal.getInstance().currentActivity, arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        ),
            PERMISSION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            val fileToUpload = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)
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
                        }
                    } else {
                        Toast.makeText(context,"UPLOAD FAILURE", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

//    override fun onProgressUpdate(percentage: Int) {
//        progressbar.progress = percentage
//    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        const val PERMISSION_CODE = 1001
    }
}