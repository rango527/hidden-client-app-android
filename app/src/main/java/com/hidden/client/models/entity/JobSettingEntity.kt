package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "JobSetting")
data class JobSettingEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    var reviewType: String,

    var isUserManager: Boolean,

    var myId: Int
)
