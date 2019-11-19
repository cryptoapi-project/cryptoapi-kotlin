package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.cryptoapi_android_framework.core.model.data.EstimatedGas
import io.pixelplex.cryptoapi_android_framework.core.model.response.EstimatedGasResponse
import io.pixelplex.cryptoapi_android_framework.support.fold
import io.pixelplex.cryptoapi_android_framework.support.future.FutureTask
import io.pixelplex.cryptoapi_android_framework.support.future.wrapResult
import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

class EthFrameworkTest {
    private var estimatedEthGas: EstimatedGasResponse? = null
    private var estimatedEthGasFail: EstimatedGasResponse? = null

    private val testFuture = FutureTask<EstimatedGasResponse>()

    private val estimatedGas = EstimatedGas(
        from = "0x141d5937C7b0e4fB4C535c900C0964B4852007eA",
        to = "0xb0202eBbF797Dd61A3b28d5E82fbA2523edc1a9B",
        value = "10"
    )

    private val badEstimatedGas = EstimatedGas(
        from = "0x141d59",
        to = "0xb0202eBbF797Dd61A3b28d5E82fbA2523edc1a9B",
        value = "10"
    )

    private val cryptoApiFramework = CryptoApiFramework.getInstance(
        CoinsFrameworkTest.CALL_TIMEOUT,
        CoinsFrameworkTest.CONNECT_TIMEOUT,
        CoinsFrameworkTest.TOKEN
    )

    @Test
    fun estimateGas() {
        cryptoApiFramework.cryptoApiEth.estimateGas(
            estimatedGas,
            { estimatedGasResp -> testFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testFuture.setComplete(estimatedGasError)}
        )

        testFuture.wrapResult<Exception, EstimatedGasResponse>(2, TimeUnit.MINUTES)
            .fold({ estimatedGasResp ->
                estimatedEthGas = estimatedGasResp
            }, { estimatedEthGas = null
            })

        assertTrue(estimatedEthGas != null)
        assertTrue(estimatedEthGas!!.errors == null)
    }

    @Test
    fun estimateGasFail() {
        cryptoApiFramework.cryptoApiEth.estimateGas(
            badEstimatedGas,
            { estimatedGasResp -> testFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testFuture.setComplete(estimatedGasError)}
        )

        testFuture.wrapResult<Exception, EstimatedGasResponse>(2, TimeUnit.MINUTES)
            .fold({ estimatedGasResp ->
                estimatedEthGasFail = estimatedGasResp
            }, { estimatedEthGasFail = null
            })

        assertTrue(estimatedEthGasFail != null)
        assertTrue(estimatedEthGasFail!!.status == INVALID_ADDRESS_ERROR)
    }

    companion object {
        const val INVALID_ADDRESS_ERROR = 422
    }
}