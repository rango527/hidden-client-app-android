package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToZero

class NullToZeroAdapter {

    @ToJson
    fun toJson(@NullToZero value: Int?): Int? {
        return value
    }

    @FromJson
    @NullToZero
    fun fromJson(@Nullable data: Int?): Int? {
        return data ?: 0
    }
}