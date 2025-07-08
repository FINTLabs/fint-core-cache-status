package no.fintlabs

import com.fasterxml.jackson.annotation.JsonAnySetter
import jakarta.annotation.PostConstruct
import jdk.jshell.Snippet
import no.fintlabs.model.CacheRequest
import no.fintlabs.model.CacheResponse
import no.fintlabs.model.ResourceInfo
import no.fintlabs.model.StatusInfo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

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

    fun getStatus(): CacheRequest {
        initCacheStatus()

        var cacheRequest: CacheRequest? = null

        for ((orgName, resources) in cacheResponse!!.orgs) {
            val org: MutableMap<String, MutableMap<String, StatusInfo>> = mutableMapOf(
                orgName to mutableMapOf()
            )
            for ((resourceName, ResourceInfo) in resources) {
                if (ResourceInfo.size != 0) {
                    val size: Int = ResourceInfo.size
                    val sinceLastUpdated: Duration = Duration.between(ResourceInfo.lastUpdated, Instant.now())
                    var status: Int
                    when {
                        sinceLastUpdated < Duration.ofMinutes(30) -> {
                            status = 1
                        }
                        sinceLastUpdated < Duration.ofHours(1) -> {
                            status = 0
                        }
                        else -> status = -1
                    }
                    org[orgName]!![resourceName] = StatusInfo(status = status, size = size)
                }
            }


        }
        return cacheRequest!!
    }


}