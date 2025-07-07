package no.fintlabs.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "fint.security.oauth2.client")
data class FintOAuth2Config (
     val username: String,
     val password: String,
)