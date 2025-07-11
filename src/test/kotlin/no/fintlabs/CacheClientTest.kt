package no.fintlabs

import no.fintlabs.client.CacheFetcher
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.Instant
import no.fintlabs.model.*
import org.mockito.kotlin.any
import kotlin.test.assertIs

class CacheClientTest {

    @Test
    fun testCacheClientGetCache() {
        val fetcher = mock<CacheFetcher>()
        whenever(fetcher.fetchCacheStatus(any())).thenReturn(mockFetchData)

        val client = CacheClient(fetcher)

        val result = client.getCache("")

        assertIs<CacheResponse>(result)
    }

    private val mockFetchData = CacheResponse(
        orgs = mapOf(
            "org" to mapOf(
                "resource" to ResourceInfo(
                    lastUpdated = Instant.parse("2025-07-10T00:57:15Z"),
                    size = 215
                )
            )
        )
    )

}