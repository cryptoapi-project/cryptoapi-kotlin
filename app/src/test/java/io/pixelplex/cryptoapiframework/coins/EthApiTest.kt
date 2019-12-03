package io.pixelplex.cryptoapiframework.coins

import io.pixelplex.cryptoapi_android_framework.CryptoApiFramework
import io.pixelplex.cryptoapiframework.BuildConfig
import io.pixelplex.cryptoapiframework.CoinsTest
import io.pixelplex.model.response.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ApiTest {

    private val apiClient = CryptoApiFramework.getInstance(
        CoinsTest.CALL_TIMEOUT,
        CoinsTest.CONNECT_TIMEOUT,
        CoinsTest.READ_TIMEOUT,
        BuildConfig.CRYPTO_API_KEY
    ).generatedApiEth

    @Test
    fun estimateGas() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthEstimatedGasResponse> {
                    apiClient.estimateGas(
                        TestValues.estimatedGas,
                        { response -> it.resume(response) },
                        { error ->
                            it.resumeWithException(error)
                        }
                    )
                }
                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun estimateGasFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthEstimatedGasResponse> {
                    apiClient.estimateGas(
                        TestValues.badEstimatedGas,
                        { response ->
                            it.resume(response)
                        },
                        { error ->
                            it.resumeWithException(error)
                        }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun getNetwork() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthNetworkResponse> {

                    apiClient.getNetwork(
                        { response ->
                            it.resume(response)
                        },
                        { error ->
                            it.resumeWithException(error)
                        }
                    )
                }

                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun getBalances() {
        runBlocking {
            try {
                val result = suspendCoroutine<List<EthBalance>> {

                    apiClient.getBalances(
                        TestValues.ethAddresses.getList(),
                        { response ->
                            it.resume(response)
                        },
                        { error ->
                            it.resumeWithException(error)
                        }
                    )
                }

                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun getBalancesFail() {

        runBlocking {
            try {
                val result = suspendCoroutine<List<EthBalance>> {
                    apiClient.getBalances(
                        TestValues.badEthAddresses.getList(),
                        { response ->
                            it.resume(response)
                        },
                        { error ->
                            it.resumeWithException(error)
                        }
                    )
                }
                println()
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun getEthInfo() {
        runBlocking {
            try {
                val result = suspendCoroutine<List<EthInfo>> {

                    apiClient.getInfo(
                        addresses = TestValues.ethAddresses.getList(),
                        onSuccess = { response ->
                            it.resume(response)
                        },
                        onError = { error ->
                            it.resumeWithException(error)
                        }
                    )
                }

                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun getEthInfoFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<List<EthInfo>> {
                    apiClient.getInfo(
                        addresses = TestValues.badEthAddresses.getList(),
                        onSuccess = { response ->
                            it.resume(response)
                        },
                        onError = { error ->
                            it.resumeWithException(error)
                        }
                    )
                }
                Assert.assertTrue(result !is List<EthInfo>)
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun getEthTransfers() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTransferResponse> {
                    apiClient.getTransfers(
                        TestValues.CONTRACT_ADDRESS,
                        TestValues.ethTransfer.typedParams.getList(),
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun getEthTransfersFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTransferResponse> {
                    apiClient.getTransfers(
                        "sdvsdvsdfvs",
                        TestValues.ethTransfer.typedParams.getList(),
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {
            }
        }
    }

    @Test
    fun getEthTransactionsExternal() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTransactionExternalResponse> {
                    apiClient.getExternalTransactions(
                        TestValues.ethTransactionExternal.typedParams.getList(),
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun getEthTransactionsExternalFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTransactionExternalResponse> {
                    apiClient.getExternalTransactions(
                        TestValues.badEthTransactionExternal.typedParams.getList(),
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun getEthTransactions() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTransactionsResponse> {
                    apiClient.getTransactions(
                        TestValues.ethTransaction.from,
                        TestValues.ethTransaction.to,
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun getEthTransactionsFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTransactionsResponse> {
                    apiClient.getTransactions(
                        TestValues.badEthTransaction.from,
                        TestValues.badEthTransaction.to,
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun getEthTransactionsByHash() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTransactionResponse> {
                    apiClient.getTransaction(
                        TestValues.HASH,
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }

    }

    @Test
    fun getEthTransactionsByHashFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTransactionResponse> {
                    apiClient.getTransaction(
                        "sdfgevsfdve",
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun getEthContractsInfo() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthContractBytecodeResponse> {
                    apiClient.getContractInfo(
                        TestValues.CONTRACT_ADDRESS,
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun getEthContractsInfoFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthContractBytecodeResponse> {
                    apiClient.getContractInfo(
                        "sfgsefewferv",
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun callEthContract() {

        runBlocking {
            try {
                val result = suspendCoroutine<String> {
                    apiClient.callContract(
                        TestValues.CONTRACT_ADDRESS,
                        TestValues.contractCallBody,
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
                Assert.assertTrue(result.isNotEmpty())
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun callEthContractFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<String> {
                    apiClient.callContract(
                        "asdfwegvwrfvwevfwrbg",
                        TestValues.contractCallBody,
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun transactionsRawSendFail() {

        runBlocking {
            try {
                val result = suspendCoroutine<String> {
                    apiClient.sendRawTransaction(
                        TestValues.ethTransactionRawBody,
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun transactionsRawDecode() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTransactionRawDecodeResponse> {
                    apiClient.decodeRawTransaction(
                        TestValues.ethTransactionRawBody,
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
                Assert.assertTrue(!result.data.isNullOrEmpty())
            } catch (e: Exception) {
                Assert.fail()
            }
        }

    }

    @Test
    fun transactionsRawDecodeFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTransactionRawDecodeResponse> {
                    apiClient.decodeRawTransaction(
                        TestValues.badEthTransactionRawBody,
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun tokensBalances() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTokenBalanceResponse> {
                    apiClient.getTokenBalances(
                        TestValues.ethTokensBalancesBody.address,
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun tokensBalancesFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTokenBalanceResponse> {
                    apiClient.getTokenBalances(
                        TestValues.badEthTokensBalancesBody.address,
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun tokensTransfers() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTokenTransferResponse> {
                    apiClient.getTokenTransfers(
                        TestValues.ethTokensTransfersBody.token,
                        TestValues.ethTokensTransfersBody.typedParams.getList(),
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun tokensTransfersFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTokenTransferResponse> {
                    apiClient.getTokenTransfers(
                        TestValues.badEthTokensTransfersBody.token,
                        TestValues.badEthTokensTransfersBody.typedParams.getList(),
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun tokenInfo() {

        runBlocking {
            try {
                val result = suspendCoroutine<EthTokenInfoResponse> {
                    apiClient.getTokenInfo(
                        TestValues.CONTRACT_ADDRESS,
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun tokenInfoFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTokenInfoResponse> {
                    apiClient.getTokenInfo(
                        "bad_address",
                        { response -> it.resume(response) },
                        { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }

    @Test
    fun tokensSearch() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTokenSearchResponse> {
                    apiClient.searchTokens(
                        query = TestValues.ethTokensSearchBody.query,
                        types = TestValues.ethTokensSearchBody.types.getList(),
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.assertNotNull(result)
                Assert.assertTrue(result.items.isNotEmpty())
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    @Test
    fun tokensSearchFail() {
        runBlocking {
            try {
                val result = suspendCoroutine<EthTokenSearchResponse> {
                    apiClient.searchTokens(
                        query = TestValues.badEthTokensSearchBody.query,
                        types = TestValues.badEthTokensSearchBody.types.getList(),
                        onSuccess = { response -> it.resume(response) },
                        onError = { error -> it.resumeWithException(error) }
                    )
                }
                Assert.fail()
            } catch (e: Exception) {

            }
        }
    }
}