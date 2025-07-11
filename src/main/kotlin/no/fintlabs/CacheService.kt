package no.fintlabs

import jakarta.annotation.PostConstruct
import no.fintlabs.config.FintProperties
import no.fintlabs.model.CacheStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

fun Instant.getStatusFromTime(): Int =
    Duration.between(this, Instant.now()).toStatusValue()

private fun Duration.toStatusValue(): Int =
    when {
        this > Duration.ofHours(1) -> -1
        this > Duration.ofMinutes(30) -> 0
        else -> 1
    }

@Service
class CacheService(
    private val cacheClient: CacheClient,
    private val metricService: MetricService,
    private val fintProperties: FintProperties
) {
    @Scheduled(fixedDelay = 60000, initialDelay = 1000)
    fun getStatus() =
        fintProperties.components
            .mapNotNull { fetchStatus(it) }
            .flatMap { it }
            .forEach { metricService.updateCacheStatus(it) }

    fun fetchStatus(component: String): List<CacheStatus>? =
        cacheClient.getCache("https://api.felleskomponent.no/${component}")
            ?.let { cache ->
                cache.orgs.flatMap { (org, resourceMap) ->
                    resourceMap.map { (resource, resourceInfo) ->
                        CacheStatus(
                            org = org,
                            component = component,
                            resource = resource,
                            status = resourceInfo.lastUpdated.getStatusFromTime()
                        )
                    }
                }
            }
}