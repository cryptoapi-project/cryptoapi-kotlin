package io.pixelplex.mobile.cryptoapi.app

import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
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
                            CryptoApiConfiguration(
                                url = CryptoApi.URL.TESTNET,
                                authorizationToken = BuildConfig.CRYPTO_API_KEY
                            )
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
                        CryptoApiConfiguration(
                            url = CryptoApi.URL.TESTNET,
                            authorizationToken = BuildConfig.CRYPTO_API_KEY
                        )
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