package com.hidden.client.helpers

enum class Environment(val env: String) {
    Production("production"),
    Development("development")
}

object APP {
    const val enviroment: String = "development"          // production or development
    const val database: String = "hiddenclient"
    const val databaseVersion: Int = 1
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