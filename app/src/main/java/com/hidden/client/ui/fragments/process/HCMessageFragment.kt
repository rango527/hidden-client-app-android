package com.hidden.client.ui.fragments.process

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hidden.client.R
import com.hidden.client.databinding.MessageListBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.ConversationFileAttachActivity
import com.hidden.client.ui.activities.ConversationTakePhotoActivity
import com.hidden.client.ui.activities.HCProcessFilterActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.MessageListVM
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.fragment_home_message.*

class HCMessageFragment(
    private val conversationId: Int
) : Fragment(), View.OnClickListener {

    private lateinit var binding: MessageListBinding
    private lateinit var viewModel: MessageListVM

    private lateinit var layoutBtnFilterSearch: LinearLayout
    private lateinit var messageSendBtn: Button
    private lateinit var attachFileBtn: ImageView
    private lateinit var takePhotoBtn: ImageView

    private lateinit var filePathTxt: TextView
    private lateinit var filePath: Uri

    //
    private var mediaPath: String? = null
    private var postPath: String? = null
    private lateinit var imageView: ImageView

    private val CAPTURE_REQUEST_CODE = 42
    private var REQUEST_PICK_PHOTO: Int = 0

    private val TAKE_PHOTO_REQUEST = 101
    private var mCurrentPhotoPath: String = ""
//    private lateinit var swipeContainer: SwipeRefreshLayout

    private lateinit var progressDlg: KProgressHUD

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
            }
            else {
                progressDlg.dismiss()
            }
        })

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = MessageListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        val view = binding.root

        binding.recyclerviewProcesses.layoutManager = LinearLayoutManager(context!!)

        layoutBtnFilterSearch = view.findViewById(R.id.layout_filter_search)
        layoutBtnFilterSearch.setOnClickListener(this)

        messageSendBtn = view.findViewById(R.id.message_send_button)
        messageSendBtn.setOnClickListener(this)

        attachFileBtn = view.findViewById(R.id.file_attachment)
        attachFileBtn.setOnClickListener(this)

        takePhotoBtn = view.findViewById(R.id.take_photo)
        takePhotoBtn.setOnClickListener(this)
//        takePhotoBtn.setOnClickListener { validatePermissions() }

        return view
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_filter_search -> {
                val intent = Intent(context, HCProcessFilterActivity::class.java)
            }

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
//                val intent = Intent()
//                intent.setType("image/*")
//                intent.setAction(Intent.ACTION_GET_CONTENT)
//                startActivityForResult(Intent.createChooser(intent, "Choose Picture"), 111)

//                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO)
                val intent = Intent(HCGlobal.getInstance().currentActivity, ConversationFileAttachActivity::class.java)
                intent.putExtra("conversationId", conversationId)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }

            R.id.take_photo -> {
//                captureImage()
//                val capture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(capture, CAPTURE_REQUEST_CODE)

                val intent = Intent(HCGlobal.getInstance().currentActivity, ConversationTakePhotoActivity::class.java)
                intent.putExtra("conversationId", conversationId)
                HCGlobal.getInstance().currentActivity.startActivity(intent)
            }
        }
    }
//
//    private fun captureImage() {
//        if (Build.VERSION.SDK_INT > 21) {
//            val callCameraApplicationIntent = Intent()
//            callCameraApplicationIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
//
//            var photoFile: File? = null
//            try {
//                photoFile = createImageFile()
//            } catch (e: IOException) {
//                Logger.getAnonymousLogger().info("Exception error")
//                e.printStackTrace()
//            }
//
//            val outputUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile)
//
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAPTURE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val takenImage = data?.extras?.get("data") as Bitmap
                imageView.setImageBitmap(takenImage)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
//
//    protected fun initDialog() {
//        pDialog = ProgressDialog(this)
//        pDialog.setMessage("Loading...")
//        pDialog.setCancelable(true)
//    }
//
//    protected fun showpDialog() {
//        if (!pDialog.isShowing)
//            pDialog.show()
//    }
//
//    protected fun hidepDialog() {
//        if (pDialog.isShowing) pDialog.dismiss()
//    }
//
//    private fun upload() {
//
//    }
//
//    companion object {
//        private val REQUEST_TAKE_PHOTO = 0
//        private val REQUEST_PICK_PHOTO = 2
//        private val CAMERA_PIC_REQUEST = 1111
//
//        private val TAG = HCMessageFragment::class.java.getSimpleName()
//
//        private val CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100
//
//
//    }

}