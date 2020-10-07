package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MessageList")
data class MessageListEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    var messageId: Int,

    var senderId: Int,

    var messageMessage: String,

    var messageUrl: String,

    var messageAssetType: String,

    var messageTime: String,

    var messageSenderType: String,

    var senderFullName: String,

    var photoUrl: String,

    var messageUnread: Boolean,

    var myId: Int                   // my Id
)