package com.hidden.client.models.json

import android.util.Log
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.ConversationEntity
import com.hidden.client.models.entity.MessageListEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConversationJson(

    @Json(name = "conversation__conversation_id")
    val conversationsId: Int?,

    @Json(name = "job__job_id")
    val jobId: Int?,

    @Json(name = "job__title")
    val jobTitle: String?,

    @Json(name = "client_asset__cloudinary_url")
    val clientAvatar: String?,

    @Json(name = "client__client_id")
    val clientId: Int?,

    @Json(name = "client__full_name")
    val clientFullName: String?,

    @Json(name = "company__name")
    val companyName: String?,

    @Json(name = "talent_partner_asset__cloudinary_url")
    val talentAvatar: String?,

    @Json(name = "talent_partner__full_name")
    val talentName: String?,

    @Json(name = "messages")
    val messages: List<MessageListJson>?
) {

    fun toConversationEntity(conversationId: Int, myId: Int): ConversationEntity {
        return ConversationEntity(
            0,
            conversationsId.safeValue(),
            jobId.safeValue(),
            jobTitle.safeValue(),
            clientAvatar.safeValue(),
            clientId.safeValue(),
            clientFullName.safeValue(),
            companyName.safeValue(),
            talentAvatar.safeValue(),
            talentName.safeValue(),
            conversationId,
            myId)
    }

    fun toMessageEntityList(myId: Int): List<MessageListEntity> {
        val messageList = ArrayList<MessageListEntity>()

        val messages = ArrayList<MessageListEntity>()

        for (message in this.messages!!) {
            messages.add(message.toEntity(myId))
        }

        messageList.addAll(messages)

        return messageList
    }
}