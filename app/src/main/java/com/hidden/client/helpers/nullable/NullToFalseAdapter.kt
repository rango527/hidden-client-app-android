package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToFalse

class NullToFalseAdapter {

    @ToJson
    fun toJson(@NullToFalse value: Boolean?): Boolean? {
        return value
    }

    @FromJson
    @NullToFalse
    fun fromJson(@Nullable data: Boolean?): Boolean? {
        return data ?: false
    }
}