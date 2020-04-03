package com.hidden.client.models.json

import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.DashboardTileContentEntity
import com.hidden.client.models.entity.DashboardTileEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DashboardTileJson(

    @Json(name = "content_type")
    val contentType: String?,

    @Json(name = "type")
    val type: String?,

    @Json(name = "title")
    val title: String?,

    @Json(name = "color_scheme")
    val colorScheme: String?,

    @Json(name = "url")
    val url: String?,

    @Json(name = "empty_status")
    val emptyStatus: String?,

    @Json(name = "empty_status_icon")
    val emptyStatusIcon: String?,

    @Json(name = "content")
    var tileContentList: List<DashboardTileContentJson>? = listOf()

) {
    fun toEntity(): DashboardTileEntity {
        return DashboardTileEntity(
            0,
            contentType.safeValue(),
            type.safeValue(),
            title.safeValue(),
            colorScheme.safeValue(),
            url.safeValue(),
            emptyStatus.safeValue(),
            emptyStatusIcon.safeValue(),
            AppPreferences.myId
        )
    }

    fun toTileContentList(pDashboardTileId: Int): List<DashboardTileContentEntity> {
        val tileContentList: ArrayList<DashboardTileContentEntity> = arrayListOf()

        for (tileContent in this.tileContentList!!) {
            tileContentList.add(tileContent.toEntity(pDashboardTileId))
        }

        return tileContentList
    }
}