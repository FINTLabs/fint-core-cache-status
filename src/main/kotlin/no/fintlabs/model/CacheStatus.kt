package no.fintlabs.model

import com.fasterxml.jackson.annotation.JsonAnySetter

data class CacheStatus(
    @JsonAnySetter
    val orgs: Map<String, Map<String, StatusInfo>>,
)

data class StatusInfo(
    val status: Int,
    val size: Int
)