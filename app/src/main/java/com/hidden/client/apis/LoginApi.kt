package com.hidden.client.apis

import com.hidden.client.models.json.LoginJson
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}