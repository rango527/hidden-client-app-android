package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Message")
data class ConversationEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    var conversationsId: Int,

    var jobId: Int,

    var jobTitle: String,

    var clientAvatar: String,

    var clientId: Int,

    var clientFullName: String,

    var companyName: String,

    var talentAvatar: String,

    var talentName: String,

    var conversationId: Int,

    var myId: Int
) {

    @Ignore
    private var messageList: ArrayList<MessageListEntity> = arrayListOf()

    fun getMessageList(): ArrayList<MessageListEntity> {
        return this.messageList
    }

    fun setMessageList(messageList: ArrayList<MessageListEntity>) {
        this.messageList = messageList
    }

}
