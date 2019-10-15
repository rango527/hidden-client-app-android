package com.hidden.client.datamodels

/**

{
    "content_type": "UPCOMING_INTERVIEW",
    "type": "DateTimeLocationTileList",
    "title": "Hideo's upcoming interviews",
    "url": "/client/dashboard/upcoming-interviews",
    "empty_status": "No interviews booked in. Make sure you're up to date with your current processes.",
    "empty_status_icon": "empty-upcoming-interviews"
}

 */
data class HCDashboardResponse(
    val content_type: String,
    val type: String,
    val title: String,
    val url: String,
    val empty_status: String,
    val empty_status_icon: String,
    val color_scheme: String) {

}