package com.hidden.client.ui.fragments.process

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
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
import com.hidden.client.ui.dialogs.HToast
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutSendMessage: LinearLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var textMessage: EditText

    private lateinit var attachment: MultipartBody.Part
    private lateinit var progressDlg: KProgressHUD

    private val PERMISSION_REQUEST_CODE: Int = 101

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(context!!)).get(MessageListVM::class.java)

        HCGlobal.getInstance().conversationId = conversationId
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
        swipeRefreshLayout.layoutParams.height = height - layoutSendMessageHeight - (140 * density).roundToInt()

        textMessage.doAfterTextChanged {
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

    fun onRefreshMessageFragment() {
        swipeRefreshLayout.isRefreshing = false
        viewModel.loadMessage(false, conversationId)
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
                    val intent = Intent(HCGlobal.getInstance().currentActivity, ConversationFileAttachActivity::class.java)
                    intent.putExtra("conversationId", conversationId)
                    HCGlobal.getInstance().currentActivity.startActivity(intent)
                } else {
                    requestPermission()
                }
            }

            R.id.take_photo -> {
                BottomAddMediaPickerDialog.display(
                    activity!!.supportFragmentManager,
                    this,
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
//                            onRefreshMessageFragment()
                            HToast.show(HCGlobal.getInstance().currentActivity, "Success file upload", HToast.TOAST_SUCCESS)
                        } else {
                            HToast.show(HCGlobal.getInstance().currentActivity, "Upload Failure!", HToast.TOAST_ERROR)
                        }
                    } else {
                        HToast.show(HCGlobal.getInstance().currentActivity, "Upload Failure!", HToast.TOAST_ERROR)
                    }
                }
            })
        }
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        const val PERMISSION_CODE = 1001
        fun newInstance(): HCMessageFragment {
            return HCMessageFragment(HCGlobal.getInstance().conversationId)
        }
    }
}