package com.hidden.client.apis

import com.hidden.client.models.json.*
import io.reactivex.Observable
import okhttp3.RequestBody
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
    @GET("client/processes/{process_id}/details")
    fun getProcessDetail(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int
    ): Observable<ProcessJson>

    @GET("client/processes/{process_id}/interviews/{interview_id}/possible-interviewers")
    fun getAvailableInterviewersForProcess(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Path("interview_id") interviewId: Int
    ): Observable<List<ReviewerJson>>

    @POST("client/processes/{process_id}/interviews/{interview_id}/interviewers/add")
    fun addInterviewersToInterview(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Path("interview_id") interviewId: Int,
        @Body body: RequestBody
    ): Observable<SimpleResponseJson>

    /**
     * Get Timeline
     */
    @GET("client/processes/{process_id}/timeline")
    fun getTimeline(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int
    ): Observable<List<TimelineJson>>

    /**
     * Get Feedback
     * GET /client/processes/{process_id}/feedback/{feedback_id}
     */
    @GET("client/processes/{process_id}/feedback/{feedback_id}/answers")
    fun getFeedback(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Path("feedback_id") feedbackId: Int
    ): Observable<FeedbackJson>

    @POST("client/processes/{process_id}/feedback/{feedback_id}")
    fun addFeedback(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Path("feedback_id") feedbackId: Int,
        @Body body: RequestBody
    ): Observable<SimpleResponseJson>

    /**
     * Submit Feedback
     */
    @POST("/client/processes/{process_id}/submission-votes")
    fun submitFeedback(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Body body: RequestBody
    ): Observable<SubmissionVotesJson>

    /**
     * Submit Give Availability Message
     */
    @POST("/client/processes/{process_id}/interview-dates")
    fun submitInterviewProposedDates(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Body body: RequestBody
    ): Observable<SimpleResponseJson>

    /**
     * Schedule Or advance to next step
     */
    @POST("/client/processes/{process_id}/next-step")
    fun scheduleOrAdvanceToNextStep(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Body body: RequestBody
    ): Observable<SimpleResponseJson>

    @POST("client/processes/{process_id}/reject")
    fun rejectCandidate(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int
    ): Observable<SimpleResponseJson>

    /**
     * Nudge for Feedback
     */
    @POST("/client/processes/{process_id}/feedback/{feedback_id}/nudge")
    fun nudgeFeedback(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int?,
        @Path("feedback_id") feedbackId: Int?,
        @Body body: RequestBody
    ): Observable<SimpleResponseJson>

    /**
     * Get Shortlist Feedback
     * GET /client/processes/{process_id}/shortlist-feedback
     */
    @GET("client/processes/{process_id}/shortlist-feedback")
    fun getShortlistFeedback(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int
    ): Observable<ShortlistFeedbackJson>

    /**
     * Get Interview
     * GET /client/processes/{process_id}/interviews/{interview_id}
     */
    @GET("client/processes/{process_id}/interviews/{interview_id}")
    fun getInterview(
        @Header("Authorization") authToken: String,
        @Path("process_id") processId: Int,
        @Path("interview_id") interviewId: Int
    ): Observable<InterviewJson>

}