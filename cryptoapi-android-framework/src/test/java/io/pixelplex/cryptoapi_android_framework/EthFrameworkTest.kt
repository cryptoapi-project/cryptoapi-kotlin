package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.model.data.EstimatedGas
import io.pixelplex.model.data.EthAddresses
import io.pixelplex.model.data.EthTransfer
import io.pixelplex.model.response.EstimatedGasResponse
import io.pixelplex.model.response.EthBalanceResponse
import io.pixelplex.model.response.EthInfoResponse
import io.pixelplex.model.response.EthNetworkResponse
import io.pixelplex.model.response.EthTransferResponse
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
    private var ethInfo: EthInfoResponse? = null
    private var ethTransferResponse: EthTransferResponse? = null

    private val testEstimatedGasFuture = FutureTask<EstimatedGasResponse>()
    private val testEthNetworkResponseFuture = FutureTask<EthNetworkResponse>()
    private val testEthBalanceResponseFuture = FutureTask<EthBalanceResponse>()
    private val testEthInfoResponseFuture = FutureTask<EthInfoResponse>()
    private val testEthTransfersResponseFuture = FutureTask<EthTransferResponse>()

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

    private val ethAddresses = EthAddresses(ETH_ADDRESS_1, ETH_ADDRESS_2)
    private val badEthAddresses = EthAddresses(ETH_ADDRESS_1, "0xb0202eBbF797Dd61A")

    private val ethTransfer = EthTransfer(
        addresses = ethAddresses,
        skip = 0,
        limit = 1,
        positive = "positivestring"
    )

    private val badEthTransfer = EthTransfer(
        addresses = badEthAddresses,
        skip = 0,
        limit = 1,
        positive = "positivestring"
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
        cryptoApiFramework.cryptoApiEth.getBalances(
            badEthAddresses,
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

    @Test
    fun getEthInfo() {
        cryptoApiFramework.cryptoApiEth.getEthInfo(
            ethAddresses,
            { estimatedGasResp -> testEthInfoResponseFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEthInfoResponseFuture.setComplete(estimatedGasError)}
        )

        testEthInfoResponseFuture.wrapResult<Exception, EthInfoResponse>(2, TimeUnit.MINUTES)
            .fold({ ethInfoResponse ->
                ethInfo = ethInfoResponse
            }, { ethInfo = null
            })

        assertTrue(ethInfo != null)
        assertTrue(ethInfo!!.info!!.count() > 0)
    }

    @Test
    fun getEthInfoFail() {
        cryptoApiFramework.cryptoApiEth.getEthInfo(
            badEthAddresses,
            { estimatedGasResp -> testEthInfoResponseFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEthInfoResponseFuture.setComplete(estimatedGasError)}
        )

        testEthInfoResponseFuture.wrapResult<Exception, EthInfoResponse>(2, TimeUnit.MINUTES)
            .fold({ ethInfoResponse ->
                ethInfo = ethInfoResponse
            }, { ethInfo = null
            })

        assertTrue(ethInfo != null)
        assertTrue(ethInfo!!.errors!!.count() > 0)
    }

    @Test
    fun getEthTransfers() {
        cryptoApiFramework.cryptoApiEth.getEthTransfers(
            ethTransfer,
            { transferResp -> testEthTransfersResponseFuture.setComplete(transferResp) },
            { transferError -> testEthTransfersResponseFuture.setComplete(transferError) }
        )

        testEthTransfersResponseFuture.wrapResult<Exception, EthTransferResponse>(2, TimeUnit.MINUTES)
            .fold({ ethInfoResponse ->
                ethTransferResponse = ethInfoResponse
            }, { ethTransferResponse = null
            })

        assertTrue(ethTransferResponse != null)
        assertTrue(ethTransferResponse!!.items.count() > 0)
    }

    @Test
    fun getEthTransfersFail() {
        cryptoApiFramework.cryptoApiEth.getEthTransfers(
            badEthTransfer,
            { transferResp -> testEthTransfersResponseFuture.setComplete(transferResp) },
            { transferError -> testEthTransfersResponseFuture.setComplete(transferError) }
        )

        testEthTransfersResponseFuture.wrapResult<Exception, EthTransferResponse>(2, TimeUnit.MINUTES)
            .fold({ ethInfoResponse ->
                ethTransferResponse = ethInfoResponse
            }, { ethTransferResponse = null
            })

        assertTrue(ethTransferResponse != null)
        assertTrue(ethTransferResponse!!.errors!!.count() > 0)
    }

    companion object {
        const val INVALID_ADDRESS_ERROR = 422
        const val ETH_ADDRESS_1 = "0x141d5937C7b0e4fB4C535c900C0964B4852007eA"
        const val ETH_ADDRESS_2 = "0xb0202eBbF797Dd61A3b28d5E82fbA2523edc1a9B"
    }
}