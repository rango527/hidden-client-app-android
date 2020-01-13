package com.hidden.client.apis

import com.hidden.client.models.json.ShortlistJson
import com.hidden.client.models.json.SimpleResponseJson
import io.reactivex.Observable
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

    @POST("client/processes/{process_id}/reject")
    fun rejectCandidate(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int
    ): Observable<SimpleResponseJson>
}