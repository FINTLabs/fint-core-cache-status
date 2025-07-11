package no.fintlabs.model

data class CacheStatus(
    val org: String,
    val component: String,
    val resource: String,
    val status: Int
)
