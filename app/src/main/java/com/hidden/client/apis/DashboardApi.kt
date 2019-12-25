package com.hidden.client.apis

import com.hidden.client.models.json.DashboardTileContentJson
import com.hidden.client.models.json.DashboardTileJson
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface DashboardApi {

    @GET("client/dashboard")
    fun getDashboard(
        @Header("Authorization") authToken: String
    ): Observable<List<DashboardTileJson>>

    @GET
    fun getTileContent(
        @Url url: String,
        @Header("Authorization") authToken: String
    ):Call<List<DashboardTileContentJson>>
}