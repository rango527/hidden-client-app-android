package com.hidden.client.models.json

import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.MessageListEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageListJson(

    @Json(name = "message__message_id")
    val messageId: Int?,

    @Json(name = "message__sender_talent_partner_id")
    val senderId: Int?,

    @Json(name = "message__message")
    val messageMessage: String?,

    @Json(name = "asset_message__cloudinary_url")
    val messageUrl: String?,

    @Json(name = "asset_message__asset_type")
    val messageAssetType: String?,

    @Json(name = "message__created_at")
    val messageTime: String?,

    @Json(name = "message__sender_type")
    val messageSenderType: String?,

    @Json(name = "sender__full_name")
    val senderFullName: String?,

    @Json(name = "asset_sender_photo__cloudinary_url")
    val photoUrl: String?,

    @Json(name = "message__unread")
    val messageUnread: Boolean?
) {

    fun toEntity(myId: Int): MessageListEntity {
        return MessageListEntity(
            0,
            messageId.safeValue(),
            senderId.safeValue(),
            messageMessage.safeValue(),
            messageUrl.safeValue(),
            messageAssetType.safeValue(),
            messageTime.safeValue(),
            messageSenderType.safeValue(),
            senderFullName.safeValue(),
            photoUrl.safeValue(),
            messageUnread.safeValue(),
            myId
        )
    }
}