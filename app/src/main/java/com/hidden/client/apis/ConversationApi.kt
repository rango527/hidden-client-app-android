package com.hidden.client.apis

import com.hidden.client.BuildConfig
import com.hidden.client.models.json.*
import com.hidden.client.ui.fileupload.UploadResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File

interface ConversationApi {

    @GET("client/conversations/{conversation_id}")
    fun getMessage(
        @Header("Authorization") authToken: String,
        @Path("conversation_id") conversationId: Int
    ): Observable<ConversationJson>

    @FormUrlEncoded
    @POST("client/conversations/{conversation_id}")
    fun sendMessage(
        @Header("Authorization") authToken: String,
        @Path("conversation_id") conversationId: Int,
        @Field("message") message: String
    ): Observable<SimpleResponseJson>

//    @Multipart
//    @POST("client/conversations/{conversation_id}")
//    fun sendAttachFile(
//        @Header("Authorization") authToken: String,
//        @Path("conversation_id") conversationId: Int,
//        @Part attachFile: MultipartBody.Part
//    ): Observable<SimpleResponseJson>

    @Multipart
    @POST("client/conversations/{conversation_id}")
    fun uploadImage(
        @Header("Authorization") authToken: String,
        @Path("conversation_id") conversationId: Int,
        @Part attachment: MultipartBody.Part
    ): Call<UploadResponse>

    companion object {
        operator fun invoke(): ConversationApi {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ConversationApi::class.java)
        }
    }
}