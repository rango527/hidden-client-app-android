package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.json.FeedbackJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableFeedback

class NullToEmptyFeedbackList {

    @ToJson
    fun toJson(@NullableFeedback value: List<FeedbackJson>?): List<FeedbackJson>? {
        return listOf()
    }

    @FromJson
    @NullableFeedback
    fun fromJson(@Nullable data: List<FeedbackJson>?): List<FeedbackJson>? {
        return data ?: emptyList()
    }
}