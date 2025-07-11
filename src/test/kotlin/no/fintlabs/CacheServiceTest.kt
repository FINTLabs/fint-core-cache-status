package no.fintlabs

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.util.Assert
import java.time.Instant
import java.time.Duration

class CacheServiceTest {

    @Test
    fun testGetStatusFromTimeLessThan30Minutes() =
        Assert.isTrue(makeTimeStamp(29).getStatusFromTime() == 1, "29 minutes did not return 1")


    @Test
    fun testGetStatusFromTimeLessThan60Minutes() =
        Assert.isTrue(makeTimeStamp(59).getStatusFromTime() == 0, "59 minutes did not return 0")


    @Test
    fun testGetStatusFromTimeMoreThan60Minutes() =
        Assert.isTrue(makeTimeStamp(61).getStatusFromTime() == -1, "61 minutes did not return -1")

    // TODO When service is integratable with others
    @Test
    fun testCacheServiceNull() {
        val mockCacheClient = mock<CacheClient>()
        whenever(mockCacheClient.getCache("")).thenReturn(null)
        val cacheService = CacheService(mockCacheClient)

        cacheService.getStatus()
    }

    // TODO When service is integratable with others
    @Test
    fun testCacheServiceCacheResponse() {
        val mockCacheClient = mock<CacheClient>()
        whenever(mockCacheClient.getCache("")).thenReturn(null)
        val cacheService = CacheService(mockCacheClient)

        cacheService.getStatus()
    }

    private fun makeTimeStamp(difference: Long): Instant = Instant.now()
        .minus(Duration.ofMinutes(difference))
}