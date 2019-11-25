package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.CandidateBrand
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableCandidateBrand

class NullToEmptyCandidateBrandList {

    @ToJson
    fun toJson(@NullableCandidateBrand value: List<CandidateBrand>?): List<CandidateBrand>? {
        return listOf()
    }

    @FromJson
    @NullableCandidateBrand
    fun fromJson(@Nullable data: List<CandidateBrand>?): List<CandidateBrand>? {
        return data ?: emptyList()
    }
}