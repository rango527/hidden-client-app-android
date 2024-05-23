package com.hidden.client.apis

import com.hidden.client.models.json.JobSettingJson
import com.hidden.client.models.json.ReviewerJson
import com.hidden.client.models.json.SimpleResponseJson
import io.reactivex.Observable
import retrofit2.http.*

interface JobApi {

    @GET("client/jobs/{job_id}/settings")
    fun getJobSettings(
        @Header("Authorization") authToken: String,
        @Path("job_id") jobId: Int
    ): Observable<JobSettingJson>

    @GET("client/jobs/{job_id}/roles/{role}")
    fun getAvailableUsersToAddRole(
        @Header("Authorization") authToken: String,
        @Path("job_id") jobId: Int,
        @Path("role") role: String
    ): Observable<List<ReviewerJson>>

    @FormUrlEncoded
    @POST("client/jobs/{job_id}/roles/{role}/add")
    fun addUserRoleJobSetting(
        @Header("Authorization") authToken: String,
        @Path("job_id") jobId: Int,
        @Path("role") role: String,
        @Field("client_ids[]") clientIds: ArrayList<Int>,
        @Field("cascade") cascade: Boolean
    ): Observable<SimpleResponseJson>

    @FormUrlEncoded
    @POST("client/invite")
    fun inviteTeamMember(
        @Header("Authorization") authToken: String,
        @Field("first_name") firstName: String,
        @Field("surname") lastName: String,
        @Field("email") email: String,
        @Field("is_user_manager") isUserManager: Boolean
    ): Observable<SimpleResponseJson>

    @FormUrlEncoded
    @POST("client/jobs/{job_id}/roles/{role}/remove")
    fun removeUserRoleJobSetting(
        @Header("Authorization") authToken: String,
        @Path("job_id") jobId: Int,
        @Path("role") role: String,
        @Field("client_id") clientId: Int,
        @Field("cascade") cascade: Boolean
    ): Observable<SimpleResponseJson>
}