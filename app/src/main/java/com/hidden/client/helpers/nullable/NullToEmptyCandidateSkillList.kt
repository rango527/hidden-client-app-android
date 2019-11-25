package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.CandidateSkill
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableCandidateSkill

class NullToEmptyCandidateSkillList {

    @ToJson
    fun toJson(@NullableCandidateSkill value: List<CandidateSkill>?): List<CandidateSkill>? {
        return listOf()
    }

    @FromJson
    @NullableCandidateSkill
    fun fromJson(@Nullable data: List<CandidateSkill>?): List<CandidateSkill>? {
        return data ?: emptyList()
    }
}