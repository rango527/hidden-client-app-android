package com.hidden.client.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DashboardTileContent")
data class DashboardTileContentEntity (

    @field:PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val photo: String,

    val tag: String,

    val tag_colour: String,

    val title: String,

    val subTitle: String,

    val jobId: Int,

    var pDashboardTileId: Int
)