package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.json.CandidateWorkExperienceJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableCandidateWorkExperience

class NullToEmptyCandidateWorkExperienceList {

    @ToJson
    fun toJson(@NullableCandidateWorkExperience value: List<CandidateWorkExperienceJson>?): List<CandidateWorkExperienceJson>? {
        return listOf()
    }

    @FromJson
    @NullableCandidateWorkExperience
    fun fromJson(@Nullable data: List<CandidateWorkExperienceJson>?): List<CandidateWorkExperienceJson>? {
        return data ?: emptyList()
    }
}