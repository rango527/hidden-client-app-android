package com.hidden.client.apis

import com.hidden.client.datamodels.HCProfileResponse
import com.hidden.client.models.json.LoginJson
import com.hidden.client.models.json.SimpleResponseJson
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * LoginJson API
 */
interface LoginApi {

    @FormUrlEncoded
    @POST("/client/login")
    fun clientLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Observable<LoginJson>

    @FormUrlEncoded
    @POST("/client/request-password")
    fun sendPasswordRequest(
        @Field("email") email: String
    ): Observable<SimpleResponseJson>

    @FormUrlEncoded
    @POST("/client/change-password")
    fun resetPassword(
        @Header("Authorization") authToken: String,
        @Field("password") currentPassword: String,
        @Field("new_password") newPassword: String
    ): Observable<SimpleResponseJson>
}