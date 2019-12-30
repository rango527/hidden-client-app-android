package com.hidden.client.helpers.nullable

import androidx.annotation.Nullable
import com.hidden.client.models.json.SkillJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullableSkill

class NullToEmptySkillList {

    @ToJson
    fun toJson(@NullableSkill value: List<SkillJson>?): List<SkillJson>? {
        return listOf()
    }

    @FromJson
    @NullableSkill
    fun fromJson(@Nullable data: List<SkillJson>?): List<SkillJson>? {
        return data ?: emptyList()
    }
}