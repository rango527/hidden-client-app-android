package com.hidden.client.ui.fileupload

import com.google.gson.annotations.SerializedName

class UploadResponse {

    @SerializedName("error")
    var error:String? = null

    @SerializedName("message")
    var message:String? = null

}