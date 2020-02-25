package io.pixelplex.cryptoapiframework.coins.btc

import io.pixelplex.cryptoapi_android_framework.CryptoApiFramework
import io.pixelplex.cryptoapiframework.BuildConfig
import io.pixelplex.cryptoapiframework.CoinsTest
import io.pixelplex.model.data.btc.BtcOutputStatus
import io.pixelplex.model.data.btc.BtcRawTransaction
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.math.BigInteger

class BtcAsyncApiTest {

    private val apiClient = CryptoApiFramework.getInstance(
        CoinsTest.CALL_TIMEOUT,
        CoinsTest.CONNECT_TIMEOUT,
        CoinsTest.READ_TIMEOUT,
        BuildConfig.CRYPTO_API_KEY
    ).bitcoinAsyncApi

    @Test
    fun getNetwork() = runBlocking {
        try {
            apiClient.getNetwork().let { resp ->
                Assert.assertTrue(resp.lastBlock > BigInteger.ZERO)
            }

        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getBlock() = runBlocking {
        try {
            val resp = apiClient.getBlock(TestValues.BLOCK_NUMBER)
            Assert.assertTrue(resp.hash.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getBlocks() = runBlocking {
        try {
            val resp = apiClient.getBlocks()
            Assert.assertTrue(resp.items.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getTransaction() = runBlocking {
        try {
            val resp = apiClient.getTransaction(TestValues.TRANSACTION_HASH)
            Assert.assertTrue(resp.blockHash.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getTransactions() = runBlocking {
        //TODO чекнуь skip
        try {
            val resp = apiClient.getTransactions(
                TestValues.BLOCK_NUMBER,
                TestValues.BTC_FROM,
                TestValues.BTC_TO
            )
            Assert.assertTrue(resp.items.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }


    @Test
    fun getTransactionsByAddresses() = runBlocking {
        try {
            val resp = apiClient.getTransactions(
                listOf(TestValues.BTC_FROM, TestValues.BTC_TO)
            )
            Assert.assertTrue(resp.items.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun sendRawTransaction() = runBlocking {
        try {
            val resp = apiClient.sendRawTransaction(
                BtcRawTransaction(
                    TestValues.TRANSACTION_RAW_HASH
                )
            )
            Assert.assertTrue(resp.result.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun decodeRawTransaction() = runBlocking {
        try {
            val resp = apiClient.decodeRawTransaction(
                BtcRawTransaction(
                    TestValues.TRANSACTION_RAW_HASH
                )
            )
            Assert.assertTrue(resp.hash.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getOutputs() = runBlocking {
        //TODO чекнуь skip
        try {
            val resp =
                apiClient.getOutputs(
                    BtcOutputStatus.spent,
                    listOf(TestValues.BTC_FROM, TestValues.BTC_TO)
                )
            Assert.assertTrue(resp.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getAddressesWithBalances() = runBlocking {
        try {
            val resp =
                apiClient.getAddressesWithBalances(
                    listOf(TestValues.BTC_FROM, TestValues.BTC_TO)
                )
            Assert.assertTrue(resp.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun estimateFee() = runBlocking {
        try {
            val resp = apiClient.estimateFee()
            Assert.assertTrue(resp.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }
}