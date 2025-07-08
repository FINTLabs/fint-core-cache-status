package no.fintlabs

import jakarta.annotation.PostConstruct
import no.fintlabs.model.CacheResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CacheService(
    private val cacheClient: CacheClient
) {

    private val logger = LoggerFactory.getLogger(CacheService::class.java)

    @PostConstruct
    fun getCacheStatus(): CacheResponse? {
        var response: CacheResponse?

        try {
            response = cacheClient.getCache("https://api.felleskomponent.no/utdanning/vurdering")
            logger.info("CacheService GET Result: $response")
        } catch (e: Exception) {
            logger.error("Error fetching cache status", e)
        }
    }
}