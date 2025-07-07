package no.fintlabs

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TestService(
    private val cacheClient: CacheClient
) {
    private val logger = LoggerFactory.getLogger(TestService::class.java)

    @PostConstruct
    fun test() {
        try {
            val result = cacheClient.getCacheStatus("https://api.felleskomponent.no/utdanning/vurdering")
            logger.info("TestService test Result: $result")
        } catch (e: Exception) {
            logger.error("Error fetching cache status", e)
        }
    }
}