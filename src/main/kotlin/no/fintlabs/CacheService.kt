package no.fintlabs

import jakarta.annotation.PostConstruct
import no.fintlabs.model.CacheStatus
import no.fintlabs.model.CacheResponse
import no.fintlabs.model.StatusInfo
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

@Service
class CacheService(
    private val cacheClient: CacheClient
) {
    private var cacheResponse: CacheResponse? = null

    fun initCacheStatus() {
        val response = cacheClient.getCache("https://api.felleskomponent.no/utdanning/vurdering")
        cacheResponse = response
    }

    @PostConstruct
    fun getStatus(): CacheStatus {
        initCacheStatus()

        val orgs: MutableMap<String, MutableMap<String, StatusInfo>> = mutableMapOf()

        for ((orgName, resources) in cacheResponse!!.orgs) {
            val resourceMap: MutableMap<String, StatusInfo> = mutableMapOf()

            for ((resourceName, resourceInfo) in resources) {
                if (resourceInfo.size != 0) {
                    val size: Int = resourceInfo.size
                    val sinceLastUpdated: Duration = Duration.between(resourceInfo.lastUpdated, Instant.now())
                    val status: Int = when {
                        sinceLastUpdated > Duration.ofMinutes(30) -> 1
                        sinceLastUpdated > Duration.ofHours(1) -> 0
                        else -> -1
                    }
                    resourceMap[resourceName] = StatusInfo(status = status, size = size)
                }
            }
            if (resourceMap.isNotEmpty()) {
                orgs[orgName] = resourceMap
                println(resourceMap)
            }
        }
        println(CacheStatus(orgs = orgs))
        return CacheStatus(orgs = orgs)
    }
}