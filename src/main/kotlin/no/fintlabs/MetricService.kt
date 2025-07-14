package no.fintlabs

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tags
import no.fintlabs.model.CacheStatus
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class MetricService(
    private val meterRegistry: MeterRegistry,
) {
    private val gaugeValues = ConcurrentHashMap<String, GaugeValue>()

    fun updateCacheStatus(it: CacheStatus) {
        val tags = Tags.of(
            "org", it.org,
            "component", it.component,
            "resource", it.resource
        )

        val key = "${it.org}:${it.component}:${it.resource}"

        val gaugeValue = gaugeValues.computeIfAbsent(key) {
            GaugeValue().also { gv ->
                meterRegistry.gauge(
                    "fint.core.cache.health",
                    tags,
                    gv
                ) { it.value.toDouble() }
            }
        }

        gaugeValue.value = it.status
    }

    private class GaugeValue(var value: Int = 0)
}