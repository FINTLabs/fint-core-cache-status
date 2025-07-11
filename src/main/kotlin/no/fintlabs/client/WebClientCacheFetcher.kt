package no.fintlabs.client

import no.fintlabs.model.CacheResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class WebClientCacheFetcher(
    private val restClient: RestClient
) : CacheFetcher {
    override fun fetchCacheStatus(url: String): CacheResponse? {
        return restClient.get()
            .uri(url)
            .retrieve()
            .body(CacheResponse::class.java)
    }
}
