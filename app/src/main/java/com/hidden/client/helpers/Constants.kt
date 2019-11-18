package com.hidden.client.helpers

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader

enum class Environment(val env: String) {
    Production("production"),
    Development("development")
}

object APP {
    val enviroment: String = "development"          // production or development
    val database: String = "hiddenclient"
}

object API {
    const val development: String = "https://staging-api.hidden.io"
    const val production: String = "https://api.hidden.io"
    const val logEnabled: Boolean = true
}

object User {
    const val passwordMinLength: Int = 4
    const val approved: String = "APPROVED"
}

object NULL_TO_EMPTY_STRING_ADAPTER {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}
