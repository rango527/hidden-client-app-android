package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.CandidateWorkExperience
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableCandidateWorkExperience

class NullToEmptyCandidateWorkExperienceList {

    @ToJson
    fun toJson(@NullableCandidateWorkExperience value: List<CandidateWorkExperience>?): List<CandidateWorkExperience>? {
        return listOf()
    }

    @FromJson
    @NullableCandidateWorkExperience
    fun fromJson(@Nullable data: List<CandidateWorkExperience>?): List<CandidateWorkExperience>? {
        return data ?: emptyList()
    }
}