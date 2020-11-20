package com.hidden.client.datamodels

data class HCJobTileResponse (
    val tile__tile_id: Int,
    val tile__title: String,
    val tile__content: String,
    val tile__type: String,
    val tile__sort_order: Int,
    val tile_asset__cloudinary_url: String?
)