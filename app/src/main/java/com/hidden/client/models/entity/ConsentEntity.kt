package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Consent")
data class ConsentEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    val type: String,

    val acceptedVersion: String,

    val newVersion: String,

    val myId: Int
)