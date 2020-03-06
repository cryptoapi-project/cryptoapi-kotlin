package io.pixelplex.mobile.cryptoapi.app.rates

import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.app.BuildConfig
import io.pixelplex.mobile.cryptoapi.app.CoinsTest.Companion.CALL_TIMEOUT
import io.pixelplex.mobile.cryptoapi.app.CoinsTest.Companion.CONNECT_TIMEOUT
import io.pixelplex.mobile.cryptoapi.app.CoinsTest.Companion.READ_TIMEOUT
import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import io.pixelplex.mobile.cryptoapi.model.common.RateModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RatesTest {
    val ratesApi = CryptoApiFramework
        .getInstance(
            CALL_TIMEOUT,
            CONNECT_TIMEOUT,
            READ_TIMEOUT,
            BuildConfig.CRYPTO_API_KEY,
            CryptoApi.URL.TESTNET
        )
        .ratesApi

    val ratesAsyncApi = CryptoApiFramework
        .getInstance(
            CALL_TIMEOUT,
            CONNECT_TIMEOUT,
            READ_TIMEOUT,
            BuildConfig.CRYPTO_API_KEY,
            CryptoApi.URL.TESTNET
        )
        .ratesAsyncApi

    @Test
    fun ratesNotNull() {
        runBlocking {
            try {
                val result = suspendCoroutine<List<RateModel>> {
                    ratesApi.getRates(
                        TestValues.rateAddresses.getList(),
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
    fun ratesNotNullAsync() {
        runBlocking {
            try {
                val result = ratesAsyncApi.getRates(
                    TestValues.rateAddresses.getList()
                )

                Assert.assertNotNull(result)
                Assert.assertTrue(result.isNotEmpty())
            } catch (e: Exception) {
                Assert.fail()
            }
        }
    }
}