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

        val messageSenderType = message.messageSenderType
        val messageMessage = message.messageMessage
        val messageTime = message.messageTime
//        val messageSenderType = message.messageSenderType

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
        Log.d("fromDate", "fromDate $fromDate")
        val strDate = HCDate.dateToString(fromDate!!, "MM d yy HH:mm a").toString()
        Log.d("strDate", "strDate $strDate")

        if (datetem != "" && (strDate == datetem)) {
                showDate.visibility = View.GONE
        }  else {
            showDate.visibility = View.VISIBLE
        }

        if (messageSenderType == "FROM_COLLEAGUE") {
            textSendorName1.setTextColor(Color.parseColor("#4B0082"))
            textSendorName2.setTextColor(Color.parseColor("#4B0082"))
            if (messageMessage == "") {
                Log.d("messageList", "messageList22")
                showFromMessage.visibility = View.GONE
                showFromPhoto.visibility = View.VISIBLE
                showToMessage.visibility = View.GONE
                showToPhoto.visibility = View.GONE
            } else {
                showFromMessage.visibility = View.VISIBLE
                showFromPhoto.visibility = View.GONE
                showToMessage.visibility = View.GONE
                showToPhoto.visibility = View.GONE
            }
        } else if (messageSenderType == "FROM_TP") {
            textSendorName1.setTextColor(Color.parseColor("#66CC66"))
            textSendorName2.setTextColor(Color.parseColor("#66CC66"))
            if(messageMessage == "") {
                showFromMessage.visibility = View.GONE
                showFromPhoto.visibility = View.VISIBLE
                showToMessage.visibility = View.GONE
                showToPhoto.visibility = View.GONE
            } else {
                showFromMessage.visibility = View.VISIBLE
                showFromPhoto.visibility = View.GONE
                showToMessage.visibility = View.GONE
                showToPhoto.visibility = View.GONE
            }
        } else if(messageSenderType == "FROM_YOU") {
            if(messageMessage == "") {
                showFromMessage.visibility = View.GONE
                showFromPhoto.visibility = View.GONE
                showToMessage.visibility = View.GONE
                showToPhoto.visibility = View.VISIBLE
            } else {
                showFromMessage.visibility = View.GONE
                showFromPhoto.visibility = View.GONE
                showToMessage.visibility = View.VISIBLE
                showToPhoto.visibility = View.GONE
            }
        }

        datetem = strDate

        holder.itemView.setOnClickListener {

            val process = messageList[position]

            val intent = Intent(HCGlobal.getInstance().currentActivity, ProcessActivity::class.java)
            intent.putExtra("processId", process.id)
            intent.putExtra("jobId", conversationId)
            HCGlobal.getInstance().currentActivity.startActivity(intent)
        }
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