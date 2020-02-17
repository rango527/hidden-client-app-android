package com.hidden.client.models.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProcessStageActionRequiredJson (

    @Json(name = "talent_partner")
    val talentPartner: Boolean?,

    @Json(name = "client")
    val client: Boolean?,

    @Json(name = "candidate")
    val candidate: Boolean?
)