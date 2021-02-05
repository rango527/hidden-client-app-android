package com.hidden.client.apis

import com.hidden.client.datamodels.HCProfileResponse
import com.hidden.client.models.json.AcceptInviteJson
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
    @POST("/client/accept-invite")
    fun acceptInvite(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("token") code: String,
        @Field("meta") meta: String
    ): Observable<AcceptInviteJson>

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

    @FormUrlEncoded
    @POST("/client/change-forgotten-password")
    fun changeForgottenPassword(
        @Field("email") email: String,
        @Field("token") code: String,
        @Field("password") newPassword: String
    ): Observable<SimpleResponseJson>
}