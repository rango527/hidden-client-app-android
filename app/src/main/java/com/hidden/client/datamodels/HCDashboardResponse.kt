package com.hidden.client.datamodels

data class HCDashboardResponse(
    val content_type: String,
    val type: String,
    val title: String,
    val url: String,
    val empty_status: String,
    val empty_status_icon: String,
    val color_scheme: String,
    val content: List<HCJobResponse>
)