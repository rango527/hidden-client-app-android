package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Terms")
data class ConsentTermsAndPrivacyEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    val content: String,

    val summary: String,

    val version: Int
)