package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.Candidate
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToEmptyArray

class NullToEmptyArrayAdapter {

    @ToJson
    fun toJson(@NullToZero value: List<Any>?): List<Any>? {
        return listOf()
    }

    @FromJson
    @NullToZero
    fun fromJson(@Nullable data: List<Any>?): List<Any>? {
        return data ?: listOf()
    }
}