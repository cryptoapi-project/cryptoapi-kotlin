package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.model.response.*

import io.pixelplex.model.data.EthAddresses
import io.pixelplex.model.data.EthTransfer
import io.pixelplex.model.data.EthContractBytecodeResponse
import io.pixelplex.model.data.EthContractCallBody
import io.pixelplex.model.data.EthTransaction
import io.pixelplex.model.data.EthTransactionRawBody
import io.pixelplex.model.data.TransactionExternal
import io.pixelplex.cryptoapi_android_framework.support.fold
import io.pixelplex.cryptoapi_android_framework.support.future.FutureTask
import io.pixelplex.cryptoapi_android_framework.support.future.wrapResult
import io.pixelplex.model.data.EstimatedGasBody
import io.pixelplex.model.data.EthTokensBalancesBody
import io.pixelplex.model.data.TokensTransfersCallBody
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
    private var ethTransExternalResponse: TransactionExternalResponse? = null
    private var ethTranactionsResponse: EthTransactionsResponse? = null
    private var ethTranactionResponse: EthTransactionResponse? = null
    private var contractsBytecodeResponse: EthContractBytecodeResponse? = null
    private var contractResponse: EthCallContractResponse? = null
    private var ethRawContractResponse: EthTransactionRawResponse? = null
    private var ethRawDecodeResponse: EthTransactionRawDecodeResponse? = null
    private var ethTokensBalancesResponse: EthTokensBalancesResponse? = null
    private var ethTokensTransfersResponse: EthTokensTransfersResponse? = null
    private var ethTokenInfoResponse: EthTokenInfoResponse? = null

    private val testEstimatedGasFuture = FutureTask<EstimatedGasResponse>()
    private val testEthNetworkResponseFuture = FutureTask<EthNetworkResponse>()
    private val testEthBalanceResponseFuture = FutureTask<EthBalanceResponse>()
    private val testEthInfoResponseFuture = FutureTask<EthInfoResponse>()
    private val testEthTransfersResponseFuture = FutureTask<EthTransferResponse>()
    private val testEthTransactionExternalResponseFuture = FutureTask<TransactionExternalResponse>()
    private val testEthTransactionsFuture = FutureTask<EthTransactionsResponse>()
    private val testEthTransactionFuture = FutureTask<EthTransactionResponse>()
    private val testEthBytecodenFuture = FutureTask<EthContractBytecodeResponse>()
    private val testEthContractFuture = FutureTask<EthCallContractResponse>()
    private val testEthRawContractFuture = FutureTask<EthTransactionRawResponse>()
    private val testEthRawDecodeFuture = FutureTask<EthTransactionRawDecodeResponse>()
    private val testEthTokensBalancesFuture = FutureTask<EthTokensBalancesResponse>()
    private val testEthTokensTransfersFuture = FutureTask<EthTokensTransfersResponse>()
    private val testEthTokenInfoFuture = FutureTask<EthTokenInfoResponse>()

    private val estimatedGas = EstimatedGasBody(
        from = ETH_ADDRESS_1,
        to = ETH_ADDRESS_2,
        value = "10"
    )

    private val badEstimatedGas = EstimatedGasBody(
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

    private val ethTransactionExternal =
        TransactionExternal(
            addresses = ethAddresses,
            skip = 0,
            limit = 1
        )

    private val badEthTransactionExternal =
        TransactionExternal(
            addresses = badEthAddresses,
            skip = 0,
            limit = 1
        )

    private val ethTransaction = EthTransaction(
        from = ETH_ADDRESS_1,
        to = ETH_ADDRESS_2,
        skip = 0,
        limit = 3
    )

    private val badEthTransaction = EthTransaction(
        from = "0x141d5937C7b",
        to = ETH_ADDRESS_2,
        skip = 0,
        limit = 3
    )

    private val contractCallBody = EthContractCallBody(
        sender = ETH_ADDRESS_1,
        amount = 0,
        bytecode = "0x899426490000000000000000000000000000000000000000000000000000000000000001"
    )

    private val badContractCallBody =
        EthContractCallBody(
            sender = "0x141d593",
            amount = 0,
            bytecode = "0x899426490000000000000000000000000000000000000000000000000000000000000001"
        )

    private val ethTransactionRawBody =
        EthTransactionRawBody(
            tx = TX
        )

    private val badEthTransactionRawBody =
        EthTransactionRawBody(
            tx = "0xf86e8386ca0"
        )

    private val ethTokensBalancesBody =
        EthTokensBalancesBody(
            skip = 0,
            limit = 3,
            address = ETH_ADDRESS_1
        )

    private val badEthTokensBalancesBody =
        EthTokensBalancesBody(
            skip = 0,
            limit = 3,
            address = "0x141d5937C7"
        )

    private val ethTokensTransfersBody =
        TokensTransfersCallBody(
            skip = 0,
            limit = 3,
            addresses = EthAddresses(ETH_ADDRESS_1, ETH_ADDRESS_2),
            token = CONTRACT_ADDRESS
        )

    private val badEthTokensTransfersBody =
        TokensTransfersCallBody(
            skip = 0,
            limit = 3,
            addresses = EthAddresses("0xb0202eBbF79"),
            token = CONTRACT_ADDRESS
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
            { estimatedGasError -> testEstimatedGasFuture.setComplete(estimatedGasError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, EstimatedGasResponse>(2, TimeUnit.MINUTES)
            .fold({ estimatedGasResp ->
                estimatedEthGas = estimatedGasResp
            }, {
                estimatedEthGas = null
            })

        assertTrue(estimatedEthGas != null)
        assertTrue(estimatedEthGas!!.errors == null)
    }

    @Test
    fun estimateGasFail() {
        cryptoApiFramework.cryptoApiEth.estimateGas(
            badEstimatedGas,
            { estimatedGasResp -> testEstimatedGasFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEstimatedGasFuture.setComplete(estimatedGasError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, EstimatedGasResponse>(2, TimeUnit.MINUTES)
            .fold({ estimatedGasResp ->
                estimatedEthGasFail = estimatedGasResp
            }, {
                estimatedEthGasFail = null
            })

        assertTrue(estimatedEthGasFail != null)
        assertTrue(estimatedEthGasFail!!.status == INVALID_ADDRESS_ERROR)
    }

    @Test
    fun getNetwork() {
        cryptoApiFramework.cryptoApiEth.getNetwork(
            { estimatedGasResp -> testEthNetworkResponseFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEthNetworkResponseFuture.setComplete(estimatedGasError) }
        )

        testEthNetworkResponseFuture.wrapResult<Exception, EthNetworkResponse>(2, TimeUnit.MINUTES)
            .fold({ ethNetworkResponse ->
                ethNetwork = ethNetworkResponse
            }, {
                ethNetwork = null
            })

        assertTrue(ethNetwork != null)
    }

    @Test
    fun getBalances() {
        cryptoApiFramework.cryptoApiEth.getBalances(
            ethAddresses,
            { estimatedGasResp -> testEthBalanceResponseFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEthBalanceResponseFuture.setComplete(estimatedGasError) }
        )

        testEthBalanceResponseFuture.wrapResult<Exception, EthBalanceResponse>(2, TimeUnit.MINUTES)
            .fold({ ethNetworkResponse ->
                ethBalances = ethNetworkResponse
            }, {
                ethBalances = null
            })

        assertTrue(ethBalances != null)
        assertTrue(ethBalances!!.balances!!.count() > 0)
    }

    @Test
    fun getBalancesFail() {
        cryptoApiFramework.cryptoApiEth.getBalances(
            badEthAddresses,
            { estimatedGasResp -> testEthBalanceResponseFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEthBalanceResponseFuture.setComplete(estimatedGasError) }
        )

        testEthBalanceResponseFuture.wrapResult<Exception, EthBalanceResponse>(2, TimeUnit.MINUTES)
            .fold({ ethNetworkResponse ->
                ethBalances = ethNetworkResponse
            }, {
                ethBalances = null
            })

        assertTrue(ethBalances != null)
        assertTrue(ethBalances!!.errors!!.count() > 0)
    }

    @Test
    fun getEthInfo() {
        cryptoApiFramework.cryptoApiEth.getEthInfo(
            ethAddresses,
            { estimatedGasResp -> testEthInfoResponseFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEthInfoResponseFuture.setComplete(estimatedGasError) }
        )

        testEthInfoResponseFuture.wrapResult<Exception, EthInfoResponse>(2, TimeUnit.MINUTES)
            .fold({ ethInfoResponse ->
                ethInfo = ethInfoResponse
            }, {
                ethInfo = null
            })

        assertTrue(ethInfo != null)
        assertTrue(ethInfo!!.info!!.count() > 0)
    }

    @Test
    fun getEthInfoFail() {
        cryptoApiFramework.cryptoApiEth.getEthInfo(
            badEthAddresses,
            { estimatedGasResp -> testEthInfoResponseFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEthInfoResponseFuture.setComplete(estimatedGasError) }
        )

        testEthInfoResponseFuture.wrapResult<Exception, EthInfoResponse>(2, TimeUnit.MINUTES)
            .fold({ ethInfoResponse ->
                ethInfo = ethInfoResponse
            }, {
                ethInfo = null
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

        testEthTransfersResponseFuture.wrapResult<Exception, EthTransferResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTransferResponse = ethInfoResponse
            }, {
                ethTransferResponse = null
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

        testEthTransfersResponseFuture.wrapResult<Exception, EthTransferResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTransferResponse = ethInfoResponse
            }, {
                ethTransferResponse = null
            })

        assertTrue(ethTransferResponse != null)
        assertTrue(ethTransferResponse!!.errors!!.count() > 0)
    }

    @Test
    fun getEthTransactionsExternal() {
        cryptoApiFramework.cryptoApiEth.getTransactionsExternal(
            ethTransactionExternal,
            { transferResp -> testEthTransactionExternalResponseFuture.setComplete(transferResp) },
            { transferError -> testEthTransactionExternalResponseFuture.setComplete(transferError) }
        )

        testEthTransactionExternalResponseFuture.wrapResult<Exception, TransactionExternalResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTransExternalResponse = ethInfoResponse
            }, {
                ethTransExternalResponse = null
            })

        assertTrue(ethTransExternalResponse != null)
        assertTrue(ethTransExternalResponse!!.items.count() > 0)
    }

    @Test
    fun getEthTransactionsExternalFail() {
        cryptoApiFramework.cryptoApiEth.getTransactionsExternal(
            badEthTransactionExternal,
            { transferResp -> testEthTransactionExternalResponseFuture.setComplete(transferResp) },
            { transferError -> testEthTransactionExternalResponseFuture.setComplete(transferError) }
        )

        testEthTransactionExternalResponseFuture.wrapResult<Exception, TransactionExternalResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTransExternalResponse = ethInfoResponse
            }, {
                ethTransExternalResponse = null
            })

        assertTrue(ethTransExternalResponse != null)
        assertTrue(ethTransExternalResponse!!.errors!!.count() > 0)
    }

    @Test
    fun getEthTransactions() {
        cryptoApiFramework.cryptoApiEth.getEthTransactions(
            ethTransaction,
            { transferResp -> testEthTransactionsFuture.setComplete(transferResp) },
            { transferError -> testEthTransactionsFuture.setComplete(transferError) }
        )

        testEthTransactionsFuture.wrapResult<Exception, EthTransactionsResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTranactionsResponse = ethInfoResponse
            }, {
                ethTranactionsResponse = null
            })

        assertTrue(ethTranactionsResponse != null)
        assertTrue(ethTranactionsResponse!!.items.count() > 0)
    }

    @Test
    fun getEthTransactionsFail() {
        cryptoApiFramework.cryptoApiEth.getEthTransactions(
            badEthTransaction,
            { transferResp -> testEthTransactionsFuture.setComplete(transferResp) },
            { transferError -> testEthTransactionsFuture.setComplete(transferError) }
        )

        testEthTransactionsFuture.wrapResult<Exception, EthTransactionsResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTranactionsResponse = ethInfoResponse
            }, {
                ethTranactionsResponse = null
            })

        assertTrue(ethTranactionsResponse != null)
        assertTrue(ethTranactionsResponse!!.errors!!.count() > 0)
    }

    @Test
    fun getEthTransactionsByHash() {
        cryptoApiFramework.cryptoApiEth.getEthTransactionsByHash(
            HASH,
            { transferResp -> testEthTransactionFuture.setComplete(transferResp) },
            { transferError -> testEthTransactionFuture.setComplete(transferError) }
        )

        testEthTransactionFuture.wrapResult<Exception, EthTransactionResponse>(2, TimeUnit.MINUTES)
            .fold({ ethInfoResponse ->
                ethTranactionResponse = ethInfoResponse
            }, {
                ethTranactionResponse = null
            })

        assertTrue(ethTranactionResponse != null)
        assertTrue(ethTranactionResponse!!.blockNumber == 5358039L)
        assertTrue(ethTranactionResponse!!.blockHash == "0x3c690b08e73dbb0d042d855a3881f1e5a87b0ee4e892fd6e84642265797612d0")
    }

    @Test
    fun getEthTransactionsByHashFail() {
        cryptoApiFramework.cryptoApiEth.getEthTransactionsByHash(
            "0x2ebfff2a09f6",
            { transferResp -> testEthTransactionFuture.setComplete(transferResp) },
            { transferError -> testEthTransactionFuture.setComplete(transferError) }
        )

        testEthTransactionFuture.wrapResult<Exception, EthTransactionResponse>(2, TimeUnit.MINUTES)
            .fold({ ethInfoResponse ->
                ethTranactionResponse = ethInfoResponse
            }, {
                ethTranactionResponse = null
            })

        assertTrue(ethTranactionResponse != null)
        assertTrue(ethTranactionResponse!!.errors!!.count() > 0)
    }

    @Test
    fun getEthContractsInfo() {
        cryptoApiFramework.cryptoApiEth.getEthContractsInfo(
            CONTRACT_ADDRESS,
            { transferResp -> testEthBytecodenFuture.setComplete(transferResp) },
            { transferError -> testEthBytecodenFuture.setComplete(transferError) }
        )

        testEthBytecodenFuture.wrapResult<Exception, EthContractBytecodeResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                contractsBytecodeResponse = ethInfoResponse
            }, {
                contractsBytecodeResponse = null
            })

        assertTrue(contractsBytecodeResponse != null)
        assertTrue(contractsBytecodeResponse!!.bytecode.isNotEmpty())
    }

    @Test
    fun getEthContractsInfoFail() {
        cryptoApiFramework.cryptoApiEth.getEthContractsInfo(
            ETH_ADDRESS_1,
            { transferResp -> testEthBytecodenFuture.setComplete(transferResp) },
            { transferError -> testEthBytecodenFuture.setComplete(transferError) }
        )

        testEthBytecodenFuture.wrapResult<Exception, EthContractBytecodeResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                contractsBytecodeResponse = ethInfoResponse
            }, {
                contractsBytecodeResponse = null
            })

        assertTrue(contractsBytecodeResponse != null)
        assertTrue(contractsBytecodeResponse!!.errors!!.count() > 0)
    }

    @Test
    fun callEthContract() {
        cryptoApiFramework.cryptoApiEth.callContract(
            contractCallBody,
            CONTRACT_ADDRESS,
            { transferResp -> testEthContractFuture.setComplete(transferResp) },
            { transferError -> testEthContractFuture.setComplete(transferError) }
        )

        testEthContractFuture.wrapResult<Exception, EthCallContractResponse>(2, TimeUnit.MINUTES)
            .fold({ ethInfoResponse ->
                contractResponse = ethInfoResponse
            }, {
                contractResponse = null
            })

        assertTrue(contractResponse != null)
        assertTrue(contractResponse!!.response!!.isNotEmpty())
    }

    @Test
    fun callEthContractFail() {
        cryptoApiFramework.cryptoApiEth.callContract(
            badContractCallBody,
            CONTRACT_ADDRESS,
            { transferResp -> testEthContractFuture.setComplete(transferResp) },
            { transferError -> testEthContractFuture.setComplete(transferError) }
        )

        testEthContractFuture.wrapResult<Exception, EthCallContractResponse>(2, TimeUnit.MINUTES)
            .fold({ ethInfoResponse ->
                contractResponse = ethInfoResponse
            }, {
                contractResponse = null
            })

        assertTrue(contractResponse != null)
        assertTrue(contractResponse!!.errors!!.count() > 0)
    }

    @Test
    fun transactionsRawSendFail() {
        cryptoApiFramework.cryptoApiEth.transactionsRawSend(
            ethTransactionRawBody,
            { transferResp -> testEthRawContractFuture.setComplete(transferResp) },
            { transferError -> testEthRawContractFuture.setComplete(transferError) }
        )

        testEthRawContractFuture.wrapResult<Exception, EthTransactionRawResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethRawContractResponse = ethInfoResponse
            }, {
                ethRawContractResponse = null
            })

        assertTrue(ethRawContractResponse != null)
        assertTrue(ethRawContractResponse!!.errors!!.isNotEmpty())
    }

    @Test
    fun transactionsRawDecode() {
        cryptoApiFramework.cryptoApiEth.transactionsRawDecode(
            ethTransactionRawBody,
            { transferResp -> testEthRawDecodeFuture.setComplete(transferResp) },
            { transferError -> testEthRawDecodeFuture.setComplete(transferError) }
        )

        testEthRawDecodeFuture.wrapResult<Exception, EthTransactionRawDecodeResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethRawDecodeResponse = ethInfoResponse
            }, {
                ethRawDecodeResponse = null
            })

        assertTrue(ethRawDecodeResponse != null)
        assertTrue(ethRawDecodeResponse!!.v!! > 0)
    }

    @Test
    fun transactionsRawDecodeFail() {
        cryptoApiFramework.cryptoApiEth.transactionsRawDecode(
            badEthTransactionRawBody,
            { transferResp -> testEthRawDecodeFuture.setComplete(transferResp) },
            { transferError -> testEthRawDecodeFuture.setComplete(transferError) }
        )

        testEthRawDecodeFuture.wrapResult<Exception, EthTransactionRawDecodeResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethRawDecodeResponse = ethInfoResponse
            }, {
                ethRawDecodeResponse = null
            })

        assertTrue(ethRawDecodeResponse != null)
        assertTrue(ethRawDecodeResponse!!.errors!!.count() > 0)
    }

    @Test
    fun tokensBalances() {
        cryptoApiFramework.cryptoApiEth.getTokensBalances(
            ethTokensBalancesBody,
            { transferResp -> testEthTokensBalancesFuture.setComplete(transferResp) },
            { transferError -> testEthTokensBalancesFuture.setComplete(transferError) }
        )

        testEthTokensBalancesFuture.wrapResult<Exception, EthTokensBalancesResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTokensBalancesResponse = ethInfoResponse
            }, {
                ethTokensBalancesResponse = null
            })

        assertTrue(ethTokensBalancesResponse != null)
        assertTrue(ethTokensBalancesResponse!!.items.count() > 0)
    }

    @Test
    fun tokensBalancesFail() {
        cryptoApiFramework.cryptoApiEth.getTokensBalances(
            badEthTokensBalancesBody,
            { transferResp -> testEthTokensBalancesFuture.setComplete(transferResp) },
            { transferError -> testEthTokensBalancesFuture.setComplete(transferError) }
        )

        testEthTokensBalancesFuture.wrapResult<Exception, EthTokensBalancesResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTokensBalancesResponse = ethInfoResponse
            }, {
                ethTokensBalancesResponse = null
            })

        assertTrue(ethTokensBalancesResponse != null)
        assertTrue(ethTokensBalancesResponse!!.errors!!.count() > 0)
    }

    @Test
    fun tokensTransfers() {
        cryptoApiFramework.cryptoApiEth.getTokensTransfers(
            ethTokensTransfersBody,
            { transferResp -> testEthTokensTransfersFuture.setComplete(transferResp) },
            { transferError -> testEthTokensTransfersFuture.setComplete(transferError) }
        )

        testEthTokensTransfersFuture.wrapResult<Exception, EthTokensTransfersResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTokensTransfersResponse = ethInfoResponse
            }, {
                ethTokensTransfersResponse = null
            })

        assertTrue(ethTokensTransfersResponse != null)
        assertTrue(ethTokensTransfersResponse!!.items.count() > 0)
    }

    @Test
    fun tokensTransfersFail() {
        cryptoApiFramework.cryptoApiEth.getTokensTransfers(
            badEthTokensTransfersBody,
            { transferResp -> testEthTokensTransfersFuture.setComplete(transferResp) },
            { transferError -> testEthTokensTransfersFuture.setComplete(transferError) }
        )

        testEthTokensTransfersFuture.wrapResult<Exception, EthTokensTransfersResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTokensTransfersResponse = ethInfoResponse
            }, {
                ethTokensTransfersResponse = null
            })

        assertTrue(ethTokensTransfersResponse != null)
        assertTrue(ethTokensTransfersResponse!!.errors!!.count() > 0)
    }

    @Test
    fun tokenInfo() {
        cryptoApiFramework.cryptoApiEth.getTokenInfo(
            CONTRACT_ADDRESS,
            { transferResp -> testEthTokenInfoFuture.setComplete(transferResp) },
            { transferError -> testEthTokenInfoFuture.setComplete(transferError) }
        )

        testEthTokenInfoFuture.wrapResult<Exception, EthTokenInfoResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTokenInfoResponse = ethInfoResponse
            }, {
                ethTokenInfoResponse = null
            })

        assertTrue(ethTokenInfoResponse != null)
        assertTrue(ethTokenInfoResponse!!.address.isNotEmpty())
    }

    @Test
    fun tokenInfoFail() {
        cryptoApiFramework.cryptoApiEth.getTokenInfo(
            "bad_address",
            { transferResp -> testEthTokenInfoFuture.setComplete(transferResp) },
            { transferError -> testEthTokenInfoFuture.setComplete(transferError) }
        )

        testEthTokenInfoFuture.wrapResult<Exception, EthTokenInfoResponse>(
            2,
            TimeUnit.MINUTES
        )
            .fold({ ethInfoResponse ->
                ethTokenInfoResponse = ethInfoResponse
            }, {
                ethTokenInfoResponse = null
            })

        assertTrue(ethTokenInfoResponse != null)
        assertTrue(ethTokenInfoResponse!!.errors!!.isNotEmpty())
    }

    companion object {
        const val INVALID_ADDRESS_ERROR = 422
        const val ETH_ADDRESS_1 = "0x141d5937C7b0e4fB4C535c900C0964B4852007eA"
        const val ETH_ADDRESS_2 = "0xb0202eBbF797Dd61A3b28d5E82fbA2523edc1a9B"
        const val HASH = "0x2ebfff2a09f677229dced9a9d25500694f9d63e1a4cc7bf65cc635272380ac02"
        const val CONTRACT_ADDRESS = "0xf36c145eff2771ea22ece5fd87392fc8eeae719c"
        const val TX =
            "0xf86e8386ca038602bba7f5220083632ea0941de29f644d555fe9cc3241e1083de0868f959bfa8545d964b800801ca04ef1f13c58af9a9ac4be66b838a238b24db798d585d882865637fdc35bdc49c4a04b7d1dfc3d9672080347a0d3559628f5f757bd6f6a005d1c4f7cdccce020ea02"
    }
}