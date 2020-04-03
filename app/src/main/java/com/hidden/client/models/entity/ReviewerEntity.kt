package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Reviewer")
data class ReviewerEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    var clientId: Int,

    var avatar: String,

    var fullName: String,

    var reviewerType: Int,

    var settingType: Int,           // 1: Job  2: Process

    var parentId: Int,              // Id or Job Entity or Process Entity

    var myId: Int                   // my Id
)
