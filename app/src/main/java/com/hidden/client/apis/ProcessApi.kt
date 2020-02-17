package com.hidden.client.apis

import com.hidden.client.models.json.ProcessJson
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface ProcessApi {

    @GET("/client/processes")
    fun getProcessList(
        @Header("Authorization") authToken: String
    ): Observable<List<ProcessJson>>
}