package com.hidden.client.apis

import com.hidden.client.datamodels.HCClientResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("/client/login")
    fun clientLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<HCClientResponse>

}