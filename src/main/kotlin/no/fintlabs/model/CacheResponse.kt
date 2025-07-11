package no.fintlabs.model

import com.fasterxml.jackson.annotation.JsonAnySetter

data class CacheResponse(
    @JsonAnySetter
    val orgs: Map<String, Map<String, ResourceInfo>>
)
