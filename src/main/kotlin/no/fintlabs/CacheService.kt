package no.fintlabs

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

@Service
class CacheService(
    private val cacheClient: CacheClient
) {
    private fun Instant.getStatusFromTime(): Int =
        Duration.between(this, Instant.now()).toStatusValue()

    private fun Duration.toStatusValue(): Int =
        when {
            this > Duration.ofHours(1) -> -1
            this > Duration.ofMinutes(30) -> 0
            else -> 1
        }

    @PostConstruct
    fun getStatus() =
        listOf("utdanning/timeplan").forEach {
            cacheClient.getCache("https://api.felleskomponent.no/${it.replace("-", "/")}")
                ?.let {
                    it.orgs.forEach { org, resourceMap ->
                        resourceMap.forEach { resource, resourceInfo ->
                            if (resourceInfo.size > 0) {
                                println(
                                    "$org - $resource : ${
                                        resourceInfo.lastUpdated.getStatusFromTime()
                                    }"
                                )
                            }
                        }
                    }
                }
        }
}