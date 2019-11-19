package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.cryptoapi_android_framework.core.model.response.EthNetworkResponse
import io.pixelplex.cryptoapi_android_framework.support.fold
import io.pixelplex.cryptoapi_android_framework.support.future.FutureTask
import io.pixelplex.cryptoapi_android_framework.support.future.wrapResult
import io.pixelplex.model.exception.ApiException
import io.pixelplex.tools.TypedCallback
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit

class GeneratedCoinApiTest {

    @Test
    fun getNetwork() {
        var result: EthNetworkResponse? = null
        var error: ApiException? = null
        val testFuture = FutureTask<EthNetworkResponse>()

        CryptoApiFramework
            .getInstance(
                CoinsFrameworkTest.CALL_TIMEOUT,
                CoinsFrameworkTest.CONNECT_TIMEOUT,
                CoinsFrameworkTest.TOKEN
            )
            .generatedApiEth.getNetwork(
            TypedCallback.withType(EthNetworkResponse::class.java, { r ->
                testFuture.setComplete(r)
            }, { err ->
                testFuture.setComplete(err)
            })
        )

        testFuture.wrapResult<ApiException, EthNetworkResponse>(2, TimeUnit.MINUTES)
            .fold({ response ->
                result = response
            }, {
                error = it
            })

        Assert.assertNotNull(result)
        Assert.assertNull(error)
    }
}