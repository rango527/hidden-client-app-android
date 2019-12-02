package com.hidden.client.networks

import com.hidden.client.datamodels.*
import com.hidden.client.models.json.ShortlistJson
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("/client/login")
    fun clientLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<HCLoginResponse>


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

    /**
     * Get Candidates List
     * URL              :   client/crud/candidate/data?search=
     */
    @GET("client/crud/candidate/data")
    fun getCandidateList(
        @Header("Authorization") authToken: String,
        @Query("search") search: String
    ):Call<List<HCCandidateResponse>>

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

    /**
     * Dashboard
     */
    @GET("client/dashboard")
    fun getDashboard(
        @Header("Authorization") authToken: String
    ):Call<List<HCDashboardResponse>>

    @GET
    fun getUpcomingInterview(
        @Url url: String,
        @Header("Authorization") authToken: String
    ):Call<List<HCUpcomingInterviewResponse>>

    @GET
    fun getMetrics(
        @Url url: String,
        @Header("Authorization") authToken: String
    ):Call<List<HCMetricsResponse>>

    @GET
    fun getJobs(
        @Url url: String,
        @Header("Authorization") authToken: String
    ):Call<List<HCJobResponse>>

    @GET("client/jobs/{job_id}")
    fun getJobDetail(
        @Header("Authorization") authToken: String,
        @Path("job_id") jobId: String
    ):Call<HCJobDetailResponse>

    @GET("client/profile/company")
    fun getCompanyProfile(
        @Header("Authorization") authToken: String
    ):Call<HCCompanyResponse>

    /**
     * Shortlist
     */
    @GET("client/shortlist")
    fun getShortlists(
        @Header("Authorization") authToken: String
    ):Call<HCShortlistResponse>
}
