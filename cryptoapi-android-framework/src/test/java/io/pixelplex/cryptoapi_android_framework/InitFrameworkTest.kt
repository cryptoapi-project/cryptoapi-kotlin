package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.cryptoapi_android_framework.core.model.response.CoinsResponse
import io.pixelplex.cryptoapi_android_framework.support.fold
import io.pixelplex.cryptoapi_android_framework.support.future.FutureTask
import io.pixelplex.cryptoapi_android_framework.support.future.wrapResult
import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

class InitFrameworkTest {
    @Test
    fun addition_isCorrect() {

        var coinsFail: CoinsResponse? = null
        val testFuture = FutureTask<CoinsResponse>()

        CryptoApiFramework.getInstance(600, 600)
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

        testFuture.wrapResult<Exception, CoinsResponse>(1, TimeUnit.MINUTES)
            .fold({ coinsResponse ->
                coinsFail = coinsResponse
            }, {
                coinsFail = null
            })

        assertTrue(coinsFail != null)
    }
}
