package com.hidden.client.datamodels

data class HCJobResponse (
    val photo: String,
    val tag: String,
    val tag_colour: String,
    val title: String,
    val subtitle: String,
    val extra:  HCExtraResponse
) {

}