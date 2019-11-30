package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Shortlist")
data class ShortlistEntity (

    @PrimaryKey(autoGenerate = true)
    val entityId: Int,

    val clientId: String,

    val clientFullName: String,

    val clientUrl: String
)