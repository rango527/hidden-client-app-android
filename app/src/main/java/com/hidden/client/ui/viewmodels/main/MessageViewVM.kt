package com.hidden.client.ui.viewmodels.main

import androidx.lifecycle.MutableLiveData
import com.hidden.client.helpers.HCDate
import com.hidden.client.models.entity.MessageListEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class MessageViewVM: RootVM() {

    private val messageMessage = MutableLiveData<String>()
    private val messageUrl = MutableLiveData<String>()
    private val messageTime = MutableLiveData<String>()
    private val messageSenderType = MutableLiveData<String>()
    private val senderFullName = MutableLiveData<String>()
    private val photoUrl = MutableLiveData<String>()
    private val messageUnread = MutableLiveData<String>()

    fun bind(message: MessageListEntity) {
        messageMessage.value = message.messageMessage
        messageUrl.value = message.messageUrl
        messageTime.value = message.messageTime
        messageSenderType.value = message.messageSenderType
        senderFullName.value = message.senderFullName
        photoUrl.value = message.photoUrl
//        messageUnread.value = message.messageUnread
    }

    fun getMessageMessage(): MutableLiveData<String> {
        return messageMessage
    }

    fun getMessageUrl(): MutableLiveData<String> {
        return messageUrl
    }

    fun getMessageTime(): String {
        val fromDate = HCDate.stringToDate(messageTime.value!!, null)
        val strDate: String
        strDate = HCDate.dateToString(fromDate!!, "MMM d, yyyy' at 'hh:mm a").toString()
        return strDate
    }

    fun getMessageSenderType(): MutableLiveData<String> {
        return messageSenderType
    }

    fun getSenderFullName(): MutableLiveData<String> {
        return senderFullName
    }

    fun getPhotoUrl(): MutableLiveData<String> {
        return photoUrl
    }

//    fun getMessageUnread(): MutableLiveData<String> {
//        return messageUnread
//    }
}