package io.pixelplex.cryptoapi_android_framework.core

import io.pixelplex.cryptoapi_android_framework.core.model.response.CryptoApiReponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class CryptoApi(
    private val callTimeout: Long,
    private val connectTimeout: Long
) {
    private val logging by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val httpClient by lazy {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(logging)
            callTimeout(callTimeout, TimeUnit.MILLISECONDS)
            connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
        }

        builder.build()
    }

    fun <T : CryptoApiReponse> callApi(
        params: String,
        onSuccess: (T) -> Unit,
        onError: (IOException) -> Unit
    ) {
        httpClient.newCall(
            makeRequest(CRYPTO_API_URL.urlWithParams(params))
        )
    }

    private fun makeRequest(url: String) =
        Request.Builder()
            .url(url)
            .build()

    private fun String.urlWithParams(params: String) =
        this + params

    companion object {
        private const val CRYPTO_API_URL = "https://697-crypto-api.pixelplexlabs.com/api/v1/"
    }
}