package com.hidden.client.networks

import com.hidden.client.datamodels.*
import com.hidden.client.models.json.ShortlistJson
import retrofit2.Call
import retrofit2.http.*

interface Api {

    /**
     * Get Consent Content
     * URL              :   client/consent/{user-type}/{consent-type}
     * user-type        :   client|candidate
     * consent-type     :   privacy|terms
     */
    @GET("/content/consent/{user-type}/{consent-type}")
    fun getConsent(
        @Path("user-type") userType: String,
        @Path("consent-type") consentType: String
    ):Call<HCConsentResponse>

    @GET("client/candidates/{candidate_id}")
    fun getCandidateDetail(
        @Header("Authorization") authToken: String,
        @Path("candidate_id") candidateId: String
    ):Call<HCCandidateDetailResponse>

    /**
     * Profile
     */
    @GET("client/profile")
    fun getProfile(
        @Header("Authorization") authToken: String
    ):Call<HCProfileResponse>

    @GET("client/jobs/{job_id}")
    fun getJobDetail(
        @Header("Authorization") authToken: String,
        @Path("job_id") jobId: String
    ):Call<HCJobDetailResponse>

    @GET("client/profile/company")
    fun getCompanyProfile(
        @Header("Authorization") authToken: String
    ):Call<HCCompanyResponse>
}
