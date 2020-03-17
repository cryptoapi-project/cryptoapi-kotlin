package io.pixelplex.mobile.cryptoapi.app.rates

import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import io.pixelplex.mobile.cryptoapi.model.common.RateModel
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RatesTest {
    val ratesApi = CryptoApiFramework
        .getInstance(
            CryptoApiConfiguration(
                url = CryptoApi.URL.TESTNET
            )
        )
        .ratesApi

    val ratesAsyncApi = CryptoApiFramework
        .getInstance(
            CryptoApiConfiguration(
                url = CryptoApi.URL.TESTNET
            )
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