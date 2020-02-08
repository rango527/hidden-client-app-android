package com.hidden.client.apis

import com.hidden.client.models.json.JobSettingJson
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface JobApi {

    @GET("client/jobs/{job_id}/settings")
    fun getJobSettings(
        @Header("Authorization") authToken: String,
        @Path("job_id") jobId: Int
    ): Observable<JobSettingJson>

}