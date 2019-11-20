package com.hidden.client.apis

import com.hidden.client.models.Candidate
import com.hidden.client.models.Login
import io.reactivex.Observable
import retrofit2.http.*

interface CandidateApi {

    @GET("/client/crud/candidate/data")
    fun getCandidateList(
        @Header("Authorization") authToken: String,
        @Query("search") search: String
    ): Observable<List<Candidate>>

    @GET("client/candidates/{candidate_id}")
    fun getCandidateById(
        @Header("Authorization") authToken: String,
        @Path("candidate_id") candidateId: String
    ): Observable<List<Candidate>>
}