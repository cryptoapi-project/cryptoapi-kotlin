package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.cryptoapi_android_framework.core.model.response.CoinsResponse
import io.pixelplex.cryptoapi_android_framework.support.fold
import io.pixelplex.cryptoapi_android_framework.support.future.FutureTask
import io.pixelplex.cryptoapi_android_framework.support.future.wrapResult
import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

class CoinsFrameworkTest {
    @Test
    fun coinsNotNull() {
        var coins: CoinsResponse? = null
        val testFuture = FutureTask<CoinsResponse>()

        CryptoApiFramework
            .getInstance(CALL_TIMEOUT, CONNECT_TIMEOUT, TOKEN)
            .cryptoApiCoins.getCoins({ coins ->
                testFuture.setComplete(
                    coins
                )
            }, { error ->
                testFuture.setComplete(
                    error,
                    CoinsResponse(
                        arrayListOf()
                    )
                )
            })

        testFuture.wrapResult<Exception, CoinsResponse>(2, TimeUnit.MINUTES)
            .fold({ coinsResponse ->
                coins = coinsResponse
            }, {
                coins = null
            })

        assertTrue(coins != null)
    }

    companion object {
        const val CALL_TIMEOUT = 900L
        const val CONNECT_TIMEOUT = 900L
        const val TOKEN = ""
    }
}