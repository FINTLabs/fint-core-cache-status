package no.fintlabs

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tags
import no.fintlabs.model.CacheStatus
import org.springframework.stereotype.Service

@Service
class MetricService(
    private val meterRegistry: MeterRegistry,
) {
    fun updateCacheStatus(it: CacheStatus) =
        meterRegistry.gauge(
            "fint.core.cache.health",
            Tags.of(
                "org", it.org,
                "component", it.component,
                "resource", it.resource
            ),
            it.status
            )
}