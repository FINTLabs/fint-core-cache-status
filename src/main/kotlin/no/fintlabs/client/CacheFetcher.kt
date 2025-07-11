package no.fintlabs.client

import no.fintlabs.model.CacheResponse

interface CacheFetcher {
    fun fetchCacheStatus(url: String): CacheResponse?
}