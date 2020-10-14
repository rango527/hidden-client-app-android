package com.hidden.client.ui.adapters

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.androidswipelayout.SwipeLayout
import com.hidden.client.R
import com.hidden.client.databinding.*
import com.hidden.client.helpers.HCDate
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.ConversationEntity
import com.hidden.client.models.entity.MessageListEntity
import com.hidden.client.ui.activities.ProcessActivity
import com.hidden.client.ui.viewmodels.main.MessageViewVM
import kotlinx.android.synthetic.main.list_row_message_date.view.*
import java.lang.reflect.Array
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class MessageListAdapter: RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {

    private var conversationId: Int = 0
    private lateinit var messageList: ArrayList<MessageListEntity>
    private  var datetem: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MessageDateItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_row_message_date, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messageList[position])

        val message = messageList[position]
Log.d("messageList", "messageList  $message")
        val messageSenderType = message.messageSenderType
        val messageMessage = message.messageMessage
        val messageTime = message.messageTime
        val messageUrl = message.messageUrl

        // show Layout
        val showDate: ConstraintLayout = holder.itemView.findViewById(R.id.show_date)
        val showToMessage: ConstraintLayout = holder.itemView.findViewById(R.id.show_to_message)
        val showToPhoto: ConstraintLayout = holder.itemView.findViewById(R.id.show_to_photo)
        val showFromMessage: ConstraintLayout = holder.itemView.findViewById(R.id.show_from_message)
        val showFromPhoto: ConstraintLayout = holder.itemView.findViewById(R.id.show_from_photo)
        // sendor name - text color
        val textSendorName1: TextView = holder.itemView.findViewById(R.id.text_sendor_name1)
        val textSendorName2: TextView = holder.itemView.findViewById(R.id.text_sendor_name2)

        val fromDate = HCDate.stringToDate(messageTime!!, null)

        val strDate = HCDate.dateToString(fromDate!!, "MM d yy HH:mm a").toString()

        if(messageMessage != "") {
            if (datetem == "" || (strDate != datetem)) {
                showDate.visibility = View.VISIBLE
            } else {
                showDate.visibility = View.GONE
            }

            showFromPhoto.visibility = View.GONE
            showToPhoto.visibility = View.GONE

            if (messageSenderType == "FROM_COLLEAGUE") {
                textSendorName1.setTextColor(Color.parseColor("#4B0082"))
                showFromMessage.visibility = View.VISIBLE
                showToMessage.visibility = View.GONE
            } else if (messageSenderType == "FROM_TP") {
                textSendorName1.setTextColor(Color.parseColor("#66CC66"))
                showFromMessage.visibility = View.VISIBLE
                showToMessage.visibility = View.GONE
            } else if (messageSenderType == "FROM_YOU") {
                showFromMessage.visibility = View.GONE
                showToMessage.visibility = View.VISIBLE
            }
        } else if (messageUrl != "") {

            if (datetem == "" || (strDate != datetem)) {
                showDate.visibility = View.VISIBLE
            } else {
                showDate.visibility = View.GONE
            }

            showFromMessage.visibility = View.GONE
            showToMessage.visibility = View.GONE

            if(messageSenderType == "FROM_YOU") {
                showFromPhoto.visibility = View.GONE
                showToPhoto.visibility = View.VISIBLE
            } else {
                showFromPhoto.visibility = View.VISIBLE
                showToPhoto.visibility = View.GONE
                if (messageSenderType == "FROM_COLLEAGUE") {
                    textSendorName2.setTextColor(Color.parseColor("#4B0082"))
                } else if (messageSenderType == "FROM_TP") {
                    textSendorName2.setTextColor(Color.parseColor("#66CC66"))
                }
            }
        } else {
            showDate.visibility = View.GONE
            showFromMessage.visibility = View.GONE
            showFromPhoto.visibility = View.GONE
            showToMessage.visibility = View.GONE
            showToPhoto.visibility = View.GONE
        }

        datetem = strDate
    }

    override fun getItemCount(): Int {
        return if(::messageList.isInitialized) messageList.size else 0
    }

    fun updateMessageList(messageList: ArrayList<MessageListEntity>, conversationId: Int){
        this.messageList = messageList
        this.conversationId = conversationId
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: MessageDateItemBinding): RecyclerView.ViewHolder(binding.root){

        private val viewModel = MessageViewVM()

        fun bind(message: MessageListEntity){
            viewModel.bind(message)
            binding.viewModel = viewModel
        }
    }
}