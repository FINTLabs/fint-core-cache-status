package no.fintlabs.model

import java.time.Instant

data class ResourceInfo(
    val lastUpdated: Instant,
    val size: Int
)
