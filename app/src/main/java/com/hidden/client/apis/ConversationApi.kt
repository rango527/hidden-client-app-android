package com.hidden.client.apis

import com.hidden.client.models.json.*
import io.reactivex.Observable
import retrofit2.http.*

interface ConversationApi {

    @GET("client/conversations/{conversation_id}")
    fun getMessage(
        @Header("Authorization") authToken: String,
        @Path("conversation_id") conversationId: Int
    ): Observable<ConversationJson>


}