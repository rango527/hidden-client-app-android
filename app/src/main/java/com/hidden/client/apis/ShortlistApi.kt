package com.hidden.client.apis

import com.hidden.client.models.json.ConsentJson
import com.hidden.client.models.json.ConsentTermsAndPrivacyJson
import com.hidden.client.models.json.ShortlistJson
import com.hidden.client.models.json.SimpleResponseJson
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ShortlistApi {

    @GET("/client/shortlist")
    fun getShortlist(
        @Header("Authorization") authToken: String
    ): Observable<ShortlistJson>

    @POST("client/processes/{process_id}/accept-submission")
    fun approveCandidate(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int
    ): Observable<SimpleResponseJson>


    @GET("/client/consent/need-accepting")
    fun getConsentUpdate(
        @Header("Authorization") authToken: String
    ): Observable<List<ConsentJson>>

    @GET("/content/consent/client/terms")
    fun getConsentTerms(
        @Header("Authorization") authToken: String
    ): Observable<ConsentTermsAndPrivacyJson>

    @GET("/content/consent/client/privacy")
    fun getConsentPrivacy(
        @Header("Authorization") authToken: String
    ): Observable<ConsentTermsAndPrivacyJson>

    @POST("client/consent/update-consent")
    fun updateConsent(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authToken: String,
        @Body body: RequestBody
    ): Observable<SimpleResponseJson>
}