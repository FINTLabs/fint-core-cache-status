package no.fintlabs.model

import java.time.Instant

data class CacheResponse(
    val orgs: Map<String, Map<String, ResourceInfo>>
)

data class ResourceInfo(
    val lastUpdated: Instant,
    val size: Int
)