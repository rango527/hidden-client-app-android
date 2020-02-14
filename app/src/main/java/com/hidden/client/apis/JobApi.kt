package com.hidden.client.apis

import com.hidden.client.models.json.JobSettingJson
import com.hidden.client.models.json.SimpleResponseJson
import io.reactivex.Observable
import retrofit2.http.*

interface JobApi {

    @GET("client/jobs/{job_id}/settings")
    fun getJobSettings(
        @Header("Authorization") authToken: String,
        @Path("job_id") jobId: Int
    ): Observable<JobSettingJson>

    @POST("client/jobs/{job_id}/roles/{role}/add")
    fun addUserRoleJobSetting(
        @Header("Authorization") authToken: String,
        @Path("job_id") jobId: Int,
        @Path("role") role: String,
        @Field("client_ids") clientIds: List<Int>,
        @Field("cascade") cascade: Boolean
    ): Observable<SimpleResponseJson>
}