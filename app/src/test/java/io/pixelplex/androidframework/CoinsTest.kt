package io.pixelplex.androidframework

import io.pixelplex.mobile.CryptoApiFramework
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
                            CALL_TIMEOUT,
                            CONNECT_TIMEOUT,
                            READ_TIMEOUT,
                            BuildConfig.CRYPTO_API_KEY
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
                        CALL_TIMEOUT,
                        CONNECT_TIMEOUT,
                        READ_TIMEOUT,
                        BuildConfig.CRYPTO_API_KEY
                    )
                    .coinsAsyncApi.getCoins()

                Assert.assertNotNull(result)
                Assert.assertTrue(result.isNotEmpty())

            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }

    companion object {
        const val CALL_TIMEOUT = 30000L
        const val READ_TIMEOUT = 30000L
        const val CONNECT_TIMEOUT = 15000L
    }
}