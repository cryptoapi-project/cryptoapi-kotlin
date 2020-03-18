package io.pixelplex.mobile.cryptoapi.app.coins.bch

import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import io.pixelplex.mobile.cryptoapi.model.data.btc.BtcOutputStatus
import io.pixelplex.mobile.cryptoapi.model.data.btc.BtcRawTransaction
import io.pixelplex.mobile.cryptoapi.model.data.push.FirebaseToken
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.math.BigInteger

class BchAsyncApiTest {
    private val apiClient = CryptoApiFramework.getInstance(
        CryptoApiConfiguration(
            url = CryptoApi.URL.TESTNET
        )
    ).bitcoinCashAsyncApi

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
                listOf(
                    TestValues.BTC_FROM,
                    TestValues.BTC_TO
                )
            )
            Assert.assertTrue(resp.items.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

// TODO: uncomment to test just once
//    @Test
//    fun sendRawTransaction() = runBlocking {
//        try {
//            val resp = apiClient.sendRawTransaction(
//                BtcRawTransaction(
//                    TestValues.TRANSACTION_RAW_HASH
//                )
//            )
//            Assert.assertTrue(resp.result.isNotEmpty())
//        } catch (e: Exception) {
//            Assert.fail()
//        }
//    }

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
        try {
            val resp =
                apiClient.getOutputs(
                    BtcOutputStatus.spent,
                    listOf(
                        TestValues.BTC_FROM,
                        TestValues.BTC_TO
                    )
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
                    listOf(
                        TestValues.BTC_FROM,
                        TestValues.BTC_TO
                    )
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