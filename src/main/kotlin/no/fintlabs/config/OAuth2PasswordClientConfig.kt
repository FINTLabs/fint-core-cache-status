package no.fintlabs.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository

@Configuration
class OAuth2PasswordClientConfig(
    private val registration: ClientRegistrationRepository,
    private val authorizedClientService: OAuth2AuthorizedClientService,
    private val fintOAuth2Config: FintOAuth2Config
) {
    private fun provider() =
        OAuth2AuthorizedClientProviderBuilder.builder()
            .password()
            .refreshToken()
            .build()

    @Bean
    fun oAuth2authorizedClientManager(): OAuth2AuthorizedClientManager =
        AuthorizedClientServiceOAuth2AuthorizedClientManager(
            registration, authorizedClientService
        ).also {
            it.setAuthorizedClientProvider(provider())
            it.setContextAttributesMapper {
                mapOf(
                    OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME to fintOAuth2Config.username,
                    OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME to fintOAuth2Config.password,
                )
            }
        }
}