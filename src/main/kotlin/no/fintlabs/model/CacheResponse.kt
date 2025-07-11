package no.fintlabs.model

import com.fasterxml.jackson.annotation.JsonAnySetter
import java.time.Instant

data class CacheResponse(
    @JsonAnySetter
    val orgs: Map<String, Map<String, ResourceInfo>>
)

data class ResourceInfo(
    val lastUpdated: Instant,
    val size: Int
)
