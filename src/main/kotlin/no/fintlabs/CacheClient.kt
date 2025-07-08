package no.fintlabs

import no.fintlabs.model.CacheResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientResponseException


@Component
class CacheClient(
    private val restClient: RestClient
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private fun logFetchError(e: Throwable) =
        (e as? RestClientResponseException)
            ?.let { logger.error("Error fetching cache status – {}", it.statusCode) }

    fun getCache(baseUri: String): CacheResponse? =
        runCatching {
            restClient.get()
                .uri("$baseUri/admin/cache/status")
                .retrieve()
                .body(CacheResponse::class.java)
        }.onFailure(::logFetchError)
            .getOrNull()
}