package no.fintlabs.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "fint")
data class FintProperties(
    val components: List<String>
)
