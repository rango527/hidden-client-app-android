package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.json.ProjectJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableProject

class NullToEmptyProjectList {

    @ToJson
    fun toJson(@NullableProject value: List<ProjectJson>?): List<ProjectJson>? {
        return listOf()
    }

    @FromJson
    @NullableProject
    fun fromJson(@Nullable data: List<ProjectJson>?): List<ProjectJson>? {
        return data ?: emptyList()
    }
}