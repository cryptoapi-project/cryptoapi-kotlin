package io.pixelplex.cryptoapi_android_framework.generated

import io.pixelplex.cryptoapi_android_framework.CoinsFrameworkTest
import io.pixelplex.cryptoapi_android_framework.CryptoApiFramework
import io.pixelplex.cryptoapi_android_framework.EthFrameworkTest
import io.pixelplex.model.data.EthEstimatedGasCallBody
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AsyncApiTest {

    private val apiClient = CryptoApiFramework.getInstance(
        CoinsFrameworkTest.CALL_TIMEOUT,
        CoinsFrameworkTest.CONNECT_TIMEOUT,
        CoinsFrameworkTest.READ_TIMEOUT,
        CoinsFrameworkTest.TOKEN
    ).generatedAsyncApiEth

    @Test
    fun estimateGas() = runBlocking {
        try {
            apiClient.estimateGas(TestValues.estimatedGas).let { resp ->
                Assert.assertTrue(resp.gasPrice > 0)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun estimateGasFail() = runBlocking {
        try {
            apiClient.estimateGas(TestValues.badEstimatedGas).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getNetwork() = runBlocking {
        try {
            apiClient.getNetwork().let { resp ->
                Assert.assertTrue(resp.lastBlock > 0)
            }

        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getBalances() = runBlocking {
        try {
            apiClient.getBalances(TestValues.ethAddresses.getList()).let { resp ->
                Assert.assertTrue(resp.count() > 0)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getBalancesFail() = runBlocking {
        try {
            apiClient.getBalances(TestValues.badEthAddresses.getList()).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthInfo() = runBlocking {

        try {
            apiClient.getInfo(addresses = TestValues.ethAddresses.getList()).let { resp ->
                Assert.assertTrue(resp.count() > 0)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthInfoFail() = runBlocking {
        try {
            apiClient.getInfo(addresses = listOf("badaddress")).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthTransfers() = runBlocking {
        try {
            apiClient.getTransfers(
                TestValues.CONTRACT_ADDRESS,
                TestValues.ethTransfer.typedParams.getList()
            ).let { resp ->
                Assert.assertTrue(resp.items.count() > 0)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthTransfersFail() = runBlocking {
        try {
            apiClient.getTransfers(
                "bad_address",
                TestValues.ethTransfer.typedParams.getList()
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthTransactionsExternal() = runBlocking {
        try {
            apiClient.getExternalTransactions(
                TestValues.ethTransactionExternal.typedParams.getList()
            ).let { resp ->
                Assert.assertTrue(resp.items.count() > 0)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthTransactionsExternalFail() = runBlocking {
        try {
            apiClient.getExternalTransactions(
                addresses = listOf("badaddress")
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthTransactions() = runBlocking {
        try {
            apiClient.getTransactions(
                TestValues.ethTransaction.from,
                TestValues.ethTransaction.to
            ).let { resp ->
                Assert.assertTrue(resp.items.count() > 0)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthTransactionsFail() = runBlocking {
        try {
            apiClient.getTransactions(
                TestValues.badEthTransaction.from,
                TestValues.badEthTransaction.to
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthTransactionsByHash() = runBlocking {
        try {
            apiClient.getTransaction(
                TestValues.HASH
            ).let { resp ->
                Assert.assertTrue(resp.blockNumber == 5358039L)
                Assert.assertTrue(resp.blockHash == "0x3c690b08e73dbb0d042d855a3881f1e5a87b0ee4e892fd6e84642265797612d0")
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthTransactionsByHashFail() = runBlocking {
        try {
            apiClient.getTransaction(
                "sdfsefgwerfer"
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthContractsInfo() = runBlocking {
        try {
            apiClient.getContractInfo(
                TestValues.CONTRACT_ADDRESS
            ).let { resp ->
                Assert.assertTrue(resp.bytecode.isNotEmpty())
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthContractsInfoFail() = runBlocking {
        try {
            apiClient.getContractInfo(
                "sfdvsefefv"
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun callEthContract() = runBlocking {
        try {
            apiClient.callContract(
                TestValues.CONTRACT_ADDRESS,
                TestValues.contractCallBody
            ).let { resp ->
                Assert.assertTrue(resp.isNotEmpty())
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun callEthContractFail() = runBlocking {
        try {
            apiClient.callContract(
                "segsefwerfwerf",
                TestValues.contractCallBody
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

//    @Test
//    fun transactionsRawSend() = runBlocking {
//        try {
//            apiClient.sendRawTransaction(
//                TestValues.ethTransactionRawBody
//            ).let { resp ->
//                Assert.assertTrue(resp.hash != null)
//            }
//        } catch (e: Exception) {
//            Assert.fail()
//        }
//    }

    @Test
    fun transactionsRawSendFail() = runBlocking {
        try {
            apiClient.sendRawTransaction(
                TestValues.ethTransactionRawBody
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun transactionsRawDecode() = runBlocking {
        try {
            apiClient.decodeRawTransaction(
                TestValues.ethTransactionRawBody
            ).let { resp ->
                Assert.assertTrue(resp.v!! > 0)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun transactionsRawDecodeFail() = runBlocking {
        try {
            apiClient.decodeRawTransaction(
                TestValues.badEthTransactionRawBody
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun tokensBalances() = runBlocking {
        try {
            apiClient.getTokenBalances(
                TestValues.ethTokensBalancesBody.address
            ).let { resp ->
                Assert.assertTrue(resp.items.count() > 0)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun tokensBalancesFail() = runBlocking {
        try {
            apiClient.getTokenBalances(
                TestValues.badEthTokensBalancesBody.address
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun tokensTransfers() = runBlocking {
        try {
            apiClient.getTokenTransfers(
                TestValues.ethTokensTransfersBody.token,
                TestValues.ethTokensTransfersBody.typedParams.getList()
            ).let { resp ->
                Assert.assertTrue(resp.items.count() > 0)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun tokensTransfersFail() = runBlocking {
        try {
            apiClient.getTokenTransfers(
                TestValues.badEthTokensTransfersBody.token,
                TestValues.badEthTokensTransfersBody.typedParams.getList()
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun tokenInfo() = runBlocking {
        try {
            apiClient.getTokenInfo(
                TestValues.CONTRACT_ADDRESS
            ).let { resp ->

            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun tokenInfoFail() = runBlocking {
        try {
            apiClient.getTokenInfo(
                "bad_address"
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {

        }
    }

    @Test
    fun tokensSearch() = runBlocking {
        //TODO REMOVE "r" and "3"
        try {
            apiClient.searchTokens(
                query = "r",
                limit = 3,
                types = TestValues.ethTokensSearchBody.types.getList()
            ).let { resp ->
                Assert.assertTrue(resp.items.isNotEmpty())
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun tokensSearchFail() = runBlocking {
        //TODO REMOVE "r" and "3"
        try {
            apiClient.searchTokens(
                query = "r",
                limit = 3,
                types = TestValues.badEthTokensSearchBody.types.getList()
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }
}