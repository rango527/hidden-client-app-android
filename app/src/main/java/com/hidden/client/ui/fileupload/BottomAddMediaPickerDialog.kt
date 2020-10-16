package com.hidden.client.ui.dialogs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.activities.ConversationFileAttachActivity
import com.hidden.client.ui.activities.ConversationTakePhotoActivity
import com.hidden.client.ui.activities.JobSettingActivity
import com.hidden.client.ui.activities.ProcessSettingActivity

class BottomAddMediaPickerDialog(private val conversationId: Int) : DialogFragment() {

    private lateinit var txtTakeVideo: TextView
    private lateinit var txtTakePhoto: TextView
    private lateinit var txtChooseVideo: TextView
    private lateinit var txtChoosePhoto: TextView
    private lateinit var txtCancel: TextView

    private lateinit var layoutBlack: LinearLayout

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
            val intent = Intent(HCGlobal.getInstance().currentActivity, ConversationTakePhotoActivity::class.java)
            intent.putExtra("conversationId", conversationId)
            intent.putExtra("requestCode", "TAKE_PHOTO")
            HCGlobal.getInstance().currentActivity.startActivity(intent)

            dismiss()
        }

        txtTakeVideo.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, ConversationTakePhotoActivity::class.java)
            intent.putExtra("conversationId", conversationId)
            intent.putExtra("requestCode", "TAKE_VIDEO")
            HCGlobal.getInstance().currentActivity.startActivity(intent)

            dismiss()
        }

        txtChoosePhoto.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, ConversationFileAttachActivity::class.java)
            intent.putExtra("conversationId", conversationId)
            intent.putExtra("requestCode", "CHOOSE_PHOTO")
            HCGlobal.getInstance().currentActivity.startActivity(intent)

            dismiss()
        }

        txtChooseVideo.setOnClickListener {
            val intent = Intent(HCGlobal.getInstance().currentActivity, ConversationFileAttachActivity::class.java)
            intent.putExtra("conversationId", conversationId)
            intent.putExtra("requestCode", "CHOOSE_VIDEO")
            HCGlobal.getInstance().currentActivity.startActivity(intent)

            dismiss()
        }

        layoutBlack.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "bottom_add_media_picker_dialog"
        fun display(fragmentManager: FragmentManager?, conversationId: Int): BottomAddMediaPickerDialog {
            val dialog = BottomAddMediaPickerDialog(conversationId)
            dialog.show(fragmentManager!!, TAG)
            return dialog
        }
    }
}