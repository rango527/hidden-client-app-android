package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.json.ProjectAssetsJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableProjectAssets

class NullToEmptyProjectAssetsList {

    @ToJson
    fun toJson(@NullableProjectAssets value: List<ProjectAssetsJson>?): List<ProjectAssetsJson>? {
        return listOf()
    }

    @FromJson
    @NullableProjectAssets
    fun fromJson(@Nullable data: List<ProjectAssetsJson>?): List<ProjectAssetsJson>? {
        return data?: emptyList()
    }
}