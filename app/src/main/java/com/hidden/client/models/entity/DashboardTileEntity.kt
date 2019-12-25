package com.hidden.client.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hidden.client.models.entity.CandidateEntity

@Entity(tableName = "DashboardTile")
data class DashboardTileEntity (

    @field:PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var contentType: String,

    var type: String,

    var title: String,

    var colorScheme: String,

    var url: String,

    var emptyStatus: String,

    var emptyStatusIcon: String,

    var userId: Int
) {
    @Ignore
    private var tileContentList: List<DashboardTileContentEntity> = listOf()

    fun getTileContentList(): List<DashboardTileContentEntity> {
        return tileContentList
    }

    fun setTileContentList(tileContentList: List<DashboardTileContentEntity>) {
        this.tileContentList = tileContentList
    }
}
