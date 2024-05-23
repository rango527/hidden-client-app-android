package com.hidden.client.models.json

import com.google.gson.Gson
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.DashboardTileContentEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.lang.reflect.Array
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@JsonClass(generateAdapter = true)
data class DashboardTileContentJson(

    @Json(name = "photo")
    val photo: String?,

    @Json(name = "tag")
    val tag: String?,

    @Json(name = "tag_colour")
    val tag_colour: String?,

    @Json(name = "title")
    val title: String?,

    @Json(name = "subtitle")
    val subTitle: String?,

    @Json(name = "extra")
    val extra: Any?,
//    val extra: DashboardTileExtraJson?,

    @Json(name = "value")
    val value: String?

) {
    fun toEntity(pDashboardTileId: Int): DashboardTileContentEntity {
        var jobId = 0

        if (extra != null && extra.toString().isNotEmpty()) {
            val result = extra.toString().filter { it.isDigit() }
            if (result.isNotEmpty()) {
                jobId = result.toInt()
            }
        }

        return DashboardTileContentEntity(
            0,
            photo.safeValue(),
            tag.safeValue(),
            tag_colour.safeValue(),
            title.safeValue(),
            subTitle.safeValue(),
            jobId.safeValue(),
            value.safeValue(),
            pDashboardTileId
        )
    }
}
