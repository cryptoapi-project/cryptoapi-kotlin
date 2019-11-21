package io.pixelplex.cryptoapi_android_framework.generated

import io.pixelplex.cryptoapi_android_framework.CoinsFrameworkTest
import io.pixelplex.cryptoapi_android_framework.CryptoApiFramework
import io.pixelplex.cryptoapi_android_framework.EthFrameworkTest
import io.pixelplex.model.response.EthEstimatedGasResponse
import io.pixelplex.model.response.EthNetworkResponse
import io.pixelplex.cryptoapi_android_framework.support.fold
import io.pixelplex.cryptoapi_android_framework.support.future.FutureTask
import io.pixelplex.cryptoapi_android_framework.support.future.wrapResult
import io.pixelplex.model.data.EthEstimatedGasCallBody
import io.pixelplex.model.exception.ApiException
import io.pixelplex.tools.TypedCallback
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit

class EthApiTest {

    private val cryptoApi = CryptoApiFramework
        .getInstance(
            CoinsFrameworkTest.CALL_TIMEOUT,
            CoinsFrameworkTest.CONNECT_TIMEOUT,
            CoinsFrameworkTest.TOKEN
        ).generatedApiEth

    @Test
    fun getNetwork() {
        var result: EthNetworkResponse? = null
        var error: ApiException? = null
        val testFuture = FutureTask<EthNetworkResponse>()


        cryptoApi.getNetwork(
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

    private val testEstimatedGasFuture = FutureTask<EthEstimatedGasResponse>()

    private var ethEstimatedEthGas: EthEstimatedGasResponse? = null
    private var ethEstimatedEthGasFail: EthEstimatedGasResponse? = null

    private val estimatedGas = EthEstimatedGasCallBody(
        from = EthFrameworkTest.ETH_ADDRESS_1,
        to = EthFrameworkTest.ETH_ADDRESS_2,
        value = "10"
    )

    private val badEstimatedGas = EthEstimatedGasCallBody(
        from = "0x141d59",
        to = EthFrameworkTest.ETH_ADDRESS_2,
        value = "10"
    )

//    @Test
//    fun estimateGas() {
//        cryptoApi.estimateGas(
//            estimatedGas,
//            TypedCallback.withType(EthEstimatedGasResponse::class.java, {
//                testEstimatedGasFuture.setComplete(it)
//            }, {
//                testEstimatedGasFuture.setComplete(it)
//            })
//        )
//
//        testEstimatedGasFuture.wrapResult<Exception, EthEstimatedGasResponse>(2, TimeUnit.MINUTES)
//            .fold({ estimatedGasResp ->
//                ethEstimatedEthGas = estimatedGasResp
//            }, {
//                ethEstimatedEthGas = null
//            })
//
//        Assert.assertNotNull(ethEstimatedEthGas)
//        Assert.assertNull(ethEstimatedEthGas!!.errors)
//    }
//
//    @Test
//    fun estimateGasFail() {
//        cryptoApi.estimateGas(
//            badEstimatedGas,
//            TypedCallback.withType(EthEstimatedGasResponse::class.java, {
//                testEstimatedGasFuture.setComplete(it)
//            }, {
//                testEstimatedGasFuture.setComplete(it)
//            })
//        )
//
//        testEstimatedGasFuture.wrapResult<Exception, EthEstimatedGasResponse>(2, TimeUnit.MINUTES)
//            .fold({ estimatedGasResp ->
//                ethEstimatedEthGasFail = estimatedGasResp
//            }, {
//                ethEstimatedEthGasFail = null
//            })
//
//        Assert.assertNotNull(ethEstimatedEthGasFail)
//        Assert.assertEquals(ethEstimatedEthGasFail!!.status,
//            EthFrameworkTest.INVALID_ADDRESS_ERROR
//        )
//    }
}