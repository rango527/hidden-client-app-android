package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.json.WorkExperienceJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableWorkExperience

class NullToEmptyWorkExperienceList {

    @ToJson
    fun toJson(@NullableWorkExperience value: List<WorkExperienceJson>?): List<WorkExperienceJson>? {
        return listOf()
    }

    @FromJson
    @NullableWorkExperience
    fun fromJson(@Nullable data: List<WorkExperienceJson>?): List<WorkExperienceJson>? {
        return data ?: emptyList()
    }
}