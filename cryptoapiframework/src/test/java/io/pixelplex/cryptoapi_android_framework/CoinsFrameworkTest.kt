package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.model.response.CoinsResponse
import io.pixelplex.cryptoapi_android_framework.support.fold
import io.pixelplex.cryptoapi_android_framework.support.future.FutureTask
import io.pixelplex.cryptoapi_android_framework.support.future.wrapResult
import io.pixelplex.model.response.CryptoApiResponse
import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

class CoinsFrameworkTest {
    @Test
    fun coinsNotNull() {
        val testFuture = FutureTask<CryptoApiResponse>()

        CryptoApiFramework
            .getInstance(CALL_TIMEOUT, CONNECT_TIMEOUT, TOKEN)
            .cryptoApiCoins.getCoins(
                { coins -> testFuture.setComplete(coins) },
                { error -> testFuture.setComplete(error) }
            )

        testFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ coinsResponse ->
                if (coinsResponse is CoinsResponse) {
                    assertTrue(coinsResponse.coins.isNotEmpty())
                } else {
                    fail()
                }
            }, {
                fail()
            })
    }

    companion object {
        const val CALL_TIMEOUT = 15000L
        const val CONNECT_TIMEOUT = 900L
        const val TOKEN = ""
    }
}