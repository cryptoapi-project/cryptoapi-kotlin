package io.pixelplex.cryptoapi_android_framework.generated

import io.pixelplex.cryptoapi_android_framework.CoinsFrameworkTest
import io.pixelplex.cryptoapi_android_framework.CryptoApiFramework
import io.pixelplex.cryptoapi_android_framework.EthFrameworkTest
import io.pixelplex.model.data.EstimatedGas
import io.pixelplex.model.exception.ApiException
import io.pixelplex.model.response.EstimatedGasResponse
import io.pixelplex.model.response.EthNetworkResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class EthAsyncApiTest {

    private val estimatedGas = EstimatedGas(
        from = EthFrameworkTest.ETH_ADDRESS_1,
        to = EthFrameworkTest.ETH_ADDRESS_2,
        value = "10"
    )

    private val badEstimatedGas = EstimatedGas(
        from = "0x141d59",
        to = EthFrameworkTest.ETH_ADDRESS_2,
        value = "10"
    )

    private val cryptoApi = CryptoApiFramework
        .getInstance(
            CoinsFrameworkTest.CALL_TIMEOUT,
            CoinsFrameworkTest.CONNECT_TIMEOUT,
            CoinsFrameworkTest.TOKEN
        ).generatedAsyncApiEth

    @Test
    fun getNetwork() {
        var result: EthNetworkResponse? = null
        var error: ApiException? = null

        runBlocking {
            try {
                result = cryptoApi.getNetwork()
            } catch (e: ApiException) {
                error = e
            }
        }

        Assert.assertNotNull(result)
        Assert.assertNull(error)
    }

    @Test
    fun estimateGas() {

        var estimatedEthGas: EstimatedGasResponse? = null

        runBlocking {
            try {
                estimatedEthGas = cryptoApi.estimateGas(estimatedGas)
            } catch (e: ApiException) {

            }
        }

        Assert.assertNotNull(estimatedEthGas)
        Assert.assertNull(estimatedEthGas!!.errors)
    }

    @Test
    fun estimateGasFail() {
        var estimatedEthGas: EstimatedGasResponse? = null

        runBlocking {
            try {
                estimatedEthGas = cryptoApi.estimateGas(badEstimatedGas)
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }

        Assert.assertNotNull(estimatedEthGas)
        Assert.assertEquals(
            estimatedEthGas!!.status,
            EthFrameworkTest.INVALID_ADDRESS_ERROR
        )
    }
}