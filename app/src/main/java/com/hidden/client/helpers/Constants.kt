package com.hidden.client.helpers

enum class Environment(val env: String) {
    Production("production"),
    Development("development")
}

object APP {
    const val is_debug : Boolean = true
    const val environment: String = "development"          // production or development
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

object UI {
    const val defaultSkillItemViewCount = 3
}

object Enums {
    enum class TileContentType(val value: String) {
        UPCOMING_INTERVIEW("UPCOMING_INTERVIEW"),
        SIMPLE_METRIC("SIMPLE_METRIC"),
        JOB("JOB")
    }

    enum class TileType(val value: String) {
        DATETIME_LOCATION_TILE_LIST("DateTimeLocationTileList"),
        NUMBER_TILE_LIST("NumberTileList"),
        PHOTO_TILE_LIST("PhotoTileList")
    }

    enum class TileTitle(val value: String) {
        YOUR_METRICS("Your metrics"),
        COMPANY_METRICS("Company metrics"),
        YOUR_JOBS("Your jobs"),
        COLLEAGUE_JOBS(" Colleagues' jobs")
    }

    enum class TileColorScheme(val value: String) {
        LIGHT("light"),
        DARK("dark")
    }

    enum class Resource(val value: String) {
        DRAWABLE("drawable"),
        COLOR("color")
    }

    enum class ProjectAssetsType(val value: String) {
        TEXT("TEXT"),
        IMAGE("IMAGE"),
        VIDEO("VIDEO")
    }

    enum class ReviewerType(val value: Int) {
        INTERVIEWER(1),
        SHORTLIST_REVIEWER(2),
        INTERVIEWER_ADVANCER(3),
        OFFER_MANAGER(4)
    }
}