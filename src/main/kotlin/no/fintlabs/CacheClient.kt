package no.fintlabs

import no.fintlabs.client.CacheFetcher
import no.fintlabs.model.CacheResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientResponseException

@Component
class CacheClient(
    private val fetcher: CacheFetcher
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private fun logFetchError(e: Throwable) =
        (e as? RestClientResponseException)
            ?.let { logger.error("Error fetching cache status – {}", it.statusCode) }

    fun getCache(baseUri: String): CacheResponse? =
        runCatching {
            fetcher.fetchCacheStatus("$baseUri/admin/cache/status")
        }.onFailure(::logFetchError)
            .getOrNull()
}