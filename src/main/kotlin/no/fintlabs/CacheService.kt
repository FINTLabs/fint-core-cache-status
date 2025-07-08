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

    private var cacheResponse: CacheResponse? = null

    @PostConstruct
    fun initCacheStatus() {
        try {
            val response = cacheClient.getCache("https://api.felleskomponent.no/utdanning/vurdering")
            cacheResponse = response
            logger.info("initCacheStatus Result: $response")
        } catch (e: Exception) {
            logger.error("Error fetching cache status", e)
        }
    }

    fun getRawCacheStatus(): CacheResponse? = cacheResponse



}