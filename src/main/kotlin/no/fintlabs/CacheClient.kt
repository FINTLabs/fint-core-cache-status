package no.fintlabs

import no.fintlabs.model.CacheResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient


@Component
class CacheClient(
    private val restClient: RestClient
) {
    fun getCacheStatus(baseUri: String): CacheResponse? =
         restClient.get()
            .uri("$baseUri/admin/cache/status")
            .retrieve()
            .body(CacheResponse::class.java)
}