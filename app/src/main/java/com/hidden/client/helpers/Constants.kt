package com.hidden.client.helpers

enum class Environment(val env: String) {
    Production("production"),
    Development("development")
}

object APP {
    val enviroment: String = "development"          // production or development
}

object API {
    val development: String = "https://staging-api.hidden.io/"
    val production: String = "https://api.hidden.io/"
    val logEnabled: Boolean = true
}

object User {
    val passwordMinLength: Int = 4
}
