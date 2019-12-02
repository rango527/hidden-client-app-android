package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.json.SkillJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableCandidateSkill

class NullToEmptyCandidateSkillList {

    @ToJson
    fun toJson(@NullableCandidateSkill value: List<SkillJson>?): List<SkillJson>? {
        return listOf()
    }

    @FromJson
    @NullableCandidateSkill
    fun fromJson(@Nullable data: List<SkillJson>?): List<SkillJson>? {
        return data ?: emptyList()
    }
}