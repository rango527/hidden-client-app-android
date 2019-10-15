package com.hidden.client.apis

import com.hidden.client.datamodels.HCClientResponse
import com.hidden.client.datamodels.HCConsentResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("/client/login")
    fun clientLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<HCClientResponse>


    /**
     * Get Consent Content
     * user-type        : client|candidate
     * consent-type     : privacy|terms
     */
    @GET("/content/consent/{user-type}/{consent-type}")
    fun getConsent(
        @Path("user-type") userType: String,
        @Path("consent-type") consentType: String
    ):Call<HCConsentResponse>
}