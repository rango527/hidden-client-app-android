package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.CandidateProject
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableCandidateProject

class NullToEmptyCandidateProjectList {

    @ToJson
    fun toJson(@NullableCandidateProject value: List<CandidateProject>?): List<CandidateProject>? {
        return listOf()
    }

    @FromJson
    @NullableCandidateProject
    fun fromJson(@Nullable data: List<CandidateProject>?): List<CandidateProject>? {
        return data ?: emptyList()
    }
}