package no.fintlabs.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor
import org.springframework.web.client.RestClient



@Configuration
class RestClientConfig(
    private val oAuth2Manager: OAuth2AuthorizedClientManager
) {
    @Bean
    fun restClient(): RestClient =
        RestClient.builder()
            .requestInterceptor(oauth2RequestInterceptor())
            .build()

    private fun oauth2RequestInterceptor() =
        OAuth2ClientHttpRequestInterceptor(oAuth2Manager).apply{
            setClientRegistrationIdResolver { "fint" }
        }
}