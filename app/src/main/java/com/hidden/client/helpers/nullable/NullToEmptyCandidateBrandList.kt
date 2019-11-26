package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.json.CandidateBrandJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableCandidateBrand

class NullToEmptyCandidateBrandList {

    @ToJson
    fun toJson(@NullableCandidateBrand value: List<CandidateBrandJson>?): List<CandidateBrandJson>? {
        return listOf()
    }

    @FromJson
    @NullableCandidateBrand
    fun fromJson(@Nullable data: List<CandidateBrandJson>?): List<CandidateBrandJson>? {
        return data ?: emptyList()
    }
}