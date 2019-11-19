package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.cryptoapi_android_framework.core.model.data.EstimatedGas
import io.pixelplex.cryptoapi_android_framework.core.model.data.EthAddresses
import io.pixelplex.cryptoapi_android_framework.core.model.response.EstimatedGasResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthBalanceResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthNetworkResponse
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
    private var ethNetwork: EthNetworkResponse? = null
    private var ethBalances: EthBalanceResponse? = null

    private val testEstimatedGasFuture = FutureTask<EstimatedGasResponse>()
    private val testEthNetworkResponseFuture = FutureTask<EthNetworkResponse>()
    private val testEthBalanceResponseFuture = FutureTask<EthBalanceResponse>()

    private val estimatedGas = EstimatedGas(
        from = ETH_ADDRESS_1,
        to = ETH_ADDRESS_2,
        value = "10"
    )

    private val badEstimatedGas = EstimatedGas(
        from = "0x141d59",
        to = ETH_ADDRESS_2,
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
            { estimatedGasResp -> testEstimatedGasFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEstimatedGasFuture.setComplete(estimatedGasError)}
        )

        testEstimatedGasFuture.wrapResult<Exception, EstimatedGasResponse>(2, TimeUnit.MINUTES)
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
            { estimatedGasResp -> testEstimatedGasFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEstimatedGasFuture.setComplete(estimatedGasError)}
        )

        testEstimatedGasFuture.wrapResult<Exception, EstimatedGasResponse>(2, TimeUnit.MINUTES)
            .fold({ estimatedGasResp ->
                estimatedEthGasFail = estimatedGasResp
            }, { estimatedEthGasFail = null
            })

        assertTrue(estimatedEthGasFail != null)
        assertTrue(estimatedEthGasFail!!.status == INVALID_ADDRESS_ERROR)
    }

    @Test
    fun getNetwork() {
        cryptoApiFramework.cryptoApiEth.getNetwork(
            { estimatedGasResp -> testEthNetworkResponseFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEthNetworkResponseFuture.setComplete(estimatedGasError)}
        )

        testEthNetworkResponseFuture.wrapResult<Exception, EthNetworkResponse>(2, TimeUnit.MINUTES)
            .fold({ ethNetworkResponse ->
                ethNetwork = ethNetworkResponse
            }, { ethNetwork = null
            })

        assertTrue(ethNetwork != null)
    }

    @Test
    fun getBalances() {
        val ethAddresses = EthAddresses(ETH_ADDRESS_1, ETH_ADDRESS_2)

        cryptoApiFramework.cryptoApiEth.getBalances(
            ethAddresses,
            { estimatedGasResp -> testEthBalanceResponseFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEthBalanceResponseFuture.setComplete(estimatedGasError)}
        )

        testEthBalanceResponseFuture.wrapResult<Exception, EthBalanceResponse>(2, TimeUnit.MINUTES)
            .fold({ ethNetworkResponse ->
                ethBalances = ethNetworkResponse
            }, { ethBalances = null
            })

        assertTrue(ethBalances != null)
        assertTrue(ethBalances!!.balances!!.count() > 0)
    }

    @Test
    fun getBalancesFail() {
        val ethAddresses = EthAddresses(ETH_ADDRESS_1, "0xb0202eBbF797Dd61A")

        cryptoApiFramework.cryptoApiEth.getBalances(
            ethAddresses,
            { estimatedGasResp -> testEthBalanceResponseFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEthBalanceResponseFuture.setComplete(estimatedGasError)}
        )

        testEthBalanceResponseFuture.wrapResult<Exception, EthBalanceResponse>(2, TimeUnit.MINUTES)
            .fold({ ethNetworkResponse ->
                ethBalances = ethNetworkResponse
            }, { ethBalances = null
            })

        assertTrue(ethBalances != null)
        assertTrue(ethBalances!!.errors!!.count() > 0)
    }

    companion object {
        const val INVALID_ADDRESS_ERROR = 422
        const val ETH_ADDRESS_1 = "0x141d5937C7b0e4fB4C535c900C0964B4852007eA"
        const val ETH_ADDRESS_2 = "0xb0202eBbF797Dd61A3b28d5E82fbA2523edc1a9B"
    }
}