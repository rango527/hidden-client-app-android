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

    var myId: Int
)
