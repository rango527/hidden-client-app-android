package com.hidden.client.apis

import com.hidden.client.models.json.DashboardTileContentJson
import com.hidden.client.models.json.DashboardTileJson
import com.hidden.client.models.json.SimpleResponseJson
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface DashboardApi {

    @GET("client/dashboard")
    fun getDashboard(
        @Header("Authorization") authToken: String
    ): Observable<List<DashboardTileJson>>

    @GET
    fun getTileContent(
        @Url url: String,
        @Header("Authorization") authToken: String
    ):Observable<List<DashboardTileContentJson>>

    @POST("client/logout")
    fun clientLogout(
        @Header("Authorization") authToken: String
    ): Observable<SimpleResponseJson>

    @FormUrlEncoded
    @POST("client/profile")
    fun updateProfile(
        @Header("Authorization") authToken: String,
        @Field("full_name") fullName: String,
        @Field("email") email: String,
        @Field("job_title") title: String
    ): Observable<SimpleResponseJson>

    @Multipart
    @POST("client/profile")
    fun updateProfileImage(
        @Header("Authorization") authToken: String,
        @Part photo: MultipartBody.Part
    ): Observable<SimpleResponseJson>
}