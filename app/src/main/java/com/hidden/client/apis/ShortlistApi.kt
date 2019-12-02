package com.hidden.client.apis

import com.hidden.client.models.json.ShortlistJson
import io.reactivex.Observable
import retrofit2.http.*

interface ShortlistApi {

    @GET("/client/shortlist")
    fun getShortlist(
        @Header("Authorization") authToken: String
    ): Observable<ShortlistJson>
}