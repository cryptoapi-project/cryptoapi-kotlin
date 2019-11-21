package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.cryptoapi_android_framework.support.fold
import io.pixelplex.cryptoapi_android_framework.support.future.FutureTask
import io.pixelplex.cryptoapi_android_framework.support.future.wrapResult
import io.pixelplex.model.data.EstimatedGasBody
import io.pixelplex.model.data.EthContractBytecodeResponse
import io.pixelplex.model.data.EthContractCallBody
import io.pixelplex.model.data.EthTokensBalancesBody
import io.pixelplex.model.data.EthTokensSearchBody
import io.pixelplex.model.data.EthTransaction
import io.pixelplex.model.data.EthTransactionRawBody
import io.pixelplex.model.data.EthTransfer
import io.pixelplex.model.data.EthTypedParams
import io.pixelplex.model.data.TokensTransfersCallBody
import io.pixelplex.model.data.TransactionExternal
import io.pixelplex.model.response.CryptoApiResponse
import io.pixelplex.model.response.ErrorResponse
import io.pixelplex.model.response.EstimatedGasResponse
import io.pixelplex.model.response.EthBalanceResponse
import io.pixelplex.model.response.EthCallContractResponse
import io.pixelplex.model.response.EthInfoResponse
import io.pixelplex.model.response.EthNetworkResponse
import io.pixelplex.model.response.EthTokenInfoResponse
import io.pixelplex.model.response.EthTokenSearchResponse
import io.pixelplex.model.response.EthTokensBalancesResponse
import io.pixelplex.model.response.EthTokensTransfersResponse
import io.pixelplex.model.response.EthTransactionRawDecodeResponse
import io.pixelplex.model.response.EthTransactionResponse
import io.pixelplex.model.response.EthTransactionsResponse
import io.pixelplex.model.response.EthTransferResponse
import io.pixelplex.model.response.TransactionExternalResponse
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import java.util.concurrent.TimeUnit

class EthFrameworkTest {
    private val testEstimatedGasFuture = FutureTask<CryptoApiResponse>()

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

    private val ethAddresses = EthTypedParams(ETH_ADDRESS_1, ETH_ADDRESS_2)
    private val badEthAddresses = EthTypedParams(ETH_ADDRESS_1, "0xb0202eBbF797Dd61A")

    private val ethTransfer = EthTransfer(
        typedParams = ethAddresses,
        skip = 0,
        limit = 1,
        positive = "positivestring"
    )

    private val badEthTransfer = EthTransfer(
        typedParams = badEthAddresses,
        skip = 0,
        limit = 1,
        positive = "positivestring"
    )

    private val ethTransactionExternal =
        TransactionExternal(
            typedParams = ethAddresses,
            skip = 0,
            limit = 1
        )

    private val badEthTransactionExternal =
        TransactionExternal(
            typedParams = badEthAddresses,
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
            typedParams = EthTypedParams(ETH_ADDRESS_1, ETH_ADDRESS_2),
            token = CONTRACT_ADDRESS
        )

    private val badEthTokensTransfersBody =
        TokensTransfersCallBody(
            skip = 0,
            limit = 3,
            typedParams = EthTypedParams("0xb0202eBbF79"),
            token = CONTRACT_ADDRESS
        )

    private val ethTokensSearchBody =
        EthTokensSearchBody(
            skip = 0,
            limit = 3,
            types = EthTypedParams("ERC20"),
            query = "r"
        )

    private val badEthTokensSearchBody =
        EthTokensSearchBody(
            skip = 0,
            limit = 3,
            types = EthTypedParams(ETH_ADDRESS_1, ETH_ADDRESS_2),
            query = "r"
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

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EstimatedGasResponse) {
                    assertTrue(resp.gasPrice > 0)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun estimateGasFail() {
        cryptoApiFramework.cryptoApiEth.estimateGas(
            badEstimatedGas,
            { estimatedGasResp -> testEstimatedGasFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEstimatedGasFuture.setComplete(estimatedGasError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.status == INVALID_ADDRESS_ERROR)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getNetwork() {
        cryptoApiFramework.cryptoApiEth.getNetwork(
            { estimatedGasResp -> testEstimatedGasFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEstimatedGasFuture.setComplete(estimatedGasError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthNetworkResponse) {
                    assertTrue(resp.lastBlock > 0)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getBalances() {
        cryptoApiFramework.cryptoApiEth.getBalances(
            ethAddresses,
            { estimatedGasResp -> testEstimatedGasFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEstimatedGasFuture.setComplete(estimatedGasError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthBalanceResponse) {
                    assertTrue(resp.balances!!.count() > 0)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getBalancesFail() {
        cryptoApiFramework.cryptoApiEth.getBalances(
            badEthAddresses,
            { estimatedGasResp -> testEstimatedGasFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEstimatedGasFuture.setComplete(estimatedGasError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthInfo() {
        cryptoApiFramework.cryptoApiEth.getEthInfo(
            ethAddresses,
            { estimatedGasResp -> testEstimatedGasFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEstimatedGasFuture.setComplete(estimatedGasError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthInfoResponse) {
                    assertTrue(resp.info.count() > 0)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthInfoFail() {
        cryptoApiFramework.cryptoApiEth.getEthInfo(
            badEthAddresses,
            { estimatedGasResp -> testEstimatedGasFuture.setComplete(estimatedGasResp) },
            { estimatedGasError -> testEstimatedGasFuture.setComplete(estimatedGasError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthTransfers() {
        cryptoApiFramework.cryptoApiEth.getEthTransfers(
            ethTransfer,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthTransferResponse) {
                    assertTrue(resp.items.count() > 0)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthTransfersFail() {
        cryptoApiFramework.cryptoApiEth.getEthTransfers(
            badEthTransfer,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthTransactionsExternal() {
        cryptoApiFramework.cryptoApiEth.getTransactionsExternal(
            ethTransactionExternal,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is TransactionExternalResponse) {
                    assertTrue(resp.items.count() > 0)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthTransactionsExternalFail() {
        cryptoApiFramework.cryptoApiEth.getTransactionsExternal(
            badEthTransactionExternal,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthTransactions() {
        cryptoApiFramework.cryptoApiEth.getEthTransactions(
            ethTransaction,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthTransactionsResponse) {
                    assertTrue(resp.items.count() > 0)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthTransactionsFail() {
        cryptoApiFramework.cryptoApiEth.getEthTransactions(
            badEthTransaction,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthTransactionsByHash() {
        cryptoApiFramework.cryptoApiEth.getEthTransactionsByHash(
            HASH,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthTransactionResponse) {
                    assertTrue(resp.blockNumber == 5358039L)
                    assertTrue(resp.blockHash == "0x3c690b08e73dbb0d042d855a3881f1e5a87b0ee4e892fd6e84642265797612d0")
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthTransactionsByHashFail() {
        cryptoApiFramework.cryptoApiEth.getEthTransactionsByHash(
            "0x2ebfff2a09f6",
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthContractsInfo() {
        cryptoApiFramework.cryptoApiEth.getEthContractsInfo(
            CONTRACT_ADDRESS,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthContractBytecodeResponse) {
                    assertTrue(resp.bytecode.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun getEthContractsInfoFail() {
        cryptoApiFramework.cryptoApiEth.getEthContractsInfo(
            ETH_ADDRESS_1,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun callEthContract() {
        cryptoApiFramework.cryptoApiEth.callContract(
            contractCallBody,
            CONTRACT_ADDRESS,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthCallContractResponse) {
                    assertTrue(resp.response!!.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun callEthContractFail() {
        cryptoApiFramework.cryptoApiEth.callContract(
            badContractCallBody,
            CONTRACT_ADDRESS,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun transactionsRawSendFail() {
        cryptoApiFramework.cryptoApiEth.transactionsRawSend(
            ethTransactionRawBody,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun transactionsRawDecode() {
        cryptoApiFramework.cryptoApiEth.transactionsRawDecode(
            ethTransactionRawBody,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthTransactionRawDecodeResponse) {
                    assertTrue(resp.v!! > 0)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun transactionsRawDecodeFail() {
        cryptoApiFramework.cryptoApiEth.transactionsRawDecode(
            badEthTransactionRawBody,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun tokensBalances() {
        cryptoApiFramework.cryptoApiEth.getTokensBalances(
            ethTokensBalancesBody,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthTokensBalancesResponse) {
                    assertTrue(resp.items.count() > 0)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun tokensBalancesFail() {
        cryptoApiFramework.cryptoApiEth.getTokensBalances(
            badEthTokensBalancesBody,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun tokensTransfers() {
        cryptoApiFramework.cryptoApiEth.getTokensTransfers(
            ethTokensTransfersBody,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthTokensTransfersResponse) {
                    assertTrue(resp.items.count() > 0)
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun tokensTransfersFail() {
        cryptoApiFramework.cryptoApiEth.getTokensTransfers(
            badEthTokensTransfersBody,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun tokenInfo() {
        cryptoApiFramework.cryptoApiEth.getTokenInfo(
            CONTRACT_ADDRESS,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthTokenInfoResponse) {
                    assertTrue(resp.address.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun tokenInfoFail() {
        cryptoApiFramework.cryptoApiEth.getTokenInfo(
            "bad_address",
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun tokensSearch() {
        cryptoApiFramework.cryptoApiEth.getTokensSearch(
            ethTokensSearchBody,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is EthTokenSearchResponse) {
                    assertTrue(resp.items.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
    }

    @Test
    fun tokensSearchFail() {
        cryptoApiFramework.cryptoApiEth.getTokensSearch(
            badEthTokensSearchBody,
            { transferResp -> testEstimatedGasFuture.setComplete(transferResp) },
            { transferError -> testEstimatedGasFuture.setComplete(transferError) }
        )

        testEstimatedGasFuture.wrapResult<Exception, CryptoApiResponse>(2, TimeUnit.MINUTES)
            .fold({ resp ->
                if (resp is ErrorResponse) {
                    assertTrue(resp.errors.isNotEmpty())
                } else {
                    fail()
                }
            }, { fail() })
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