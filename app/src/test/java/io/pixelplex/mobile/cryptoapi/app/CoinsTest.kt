package io.pixelplex.mobile.cryptoapi.app

import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CoinsTest {
    @Test
    fun coinsNotNull() {
        runBlocking {
            try {
                val result = suspendCoroutine<List<String>> {
                    CryptoApiFramework
                        .getInstance(
                            url = CryptoApi.URL.TESTNET
                        )
                        .coinsApi.getCoins(
                        { coins -> it.resume(coins) },
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
    fun coinsNotNullAsync() {
        runBlocking {
            try {
                val result = CryptoApiFramework
                    .getInstance(
                        url = CryptoApi.URL.TESTNET
                    )
                    .coinsAsyncApi.getCoins()

                Assert.assertNotNull(result)
                Assert.assertTrue(result.isNotEmpty())
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }
}