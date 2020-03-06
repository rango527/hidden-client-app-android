package com.hidden.client.apis

import com.hidden.client.models.json.ProcessJson
import com.hidden.client.models.json.ProcessSettingJson
import com.hidden.client.models.json.ReviewerJson
import com.hidden.client.models.json.SimpleResponseJson
import io.reactivex.Observable
import retrofit2.http.*

interface ProcessApi {

    @GET("/client/processes")
    fun getProcessList(
        @Header("Authorization") authToken: String
    ): Observable<List<ProcessJson>>

    /**
     * Process Setting
     */
    @GET("client/processes/{process_id}/settings")
    fun getProcessSettings(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int
    ): Observable<ProcessSettingJson>

    @GET("client/processes/{process_id}/roles/{role}")
    fun getAvailableUsersToAddRole(
        @Header("Authorization") authToken: String,
        @Path("process_id") process_id: Int,
        @Path("role") role: String
    ): Observable<List<ReviewerJson>>

    @FormUrlEncoded
    @POST("client/processes/{process_id}/roles/{role}/add")
    fun addUserRoleProcessSetting(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Path("role") role: String,
        @Field("client_ids[]") clientIds: ArrayList<Int>
    ): Observable<SimpleResponseJson>

    @FormUrlEncoded
    @POST("client/processes/{process_id}/roles/{role}/remove")
    fun removeUserRoleProcessSetting(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Path("role") role: String,
        @Field("client_id") clientId: Int
    ): Observable<SimpleResponseJson>

    /**
     * Process Detail
     */
    @GET("client/processes/{process_id}/{interview_id}/possible-interviewers")
    fun getAvailableInterviewersForProcess(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Path("interview_id") interviewId: Int
    ): Observable<List<ReviewerJson>>

    @FormUrlEncoded
    @POST("client/processes/{process_id}/interviews/{interview_id}/interviewers/add")
    fun addInterviewersToInterview(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Path("interview_id") interviewId: Int,
        @Field("client_ids") clientIds: List<Int>
    ): Observable<SimpleResponseJson>
}