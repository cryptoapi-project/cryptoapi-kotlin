package io.pixelplex.cryptoapi_android_framework.core

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class CryptoApi(
    private val callTimeout: Long,
    private val connectTimeout: Long,
    private val token: String
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

    fun callApi(
        params: String,
        onSuccess: (String) -> Unit,
        onError: (IOException) -> Unit
    ) {
        httpClient.newCall(
            makeRequest(CRYPTO_API_URL.urlWithParams(params))
        ).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                onSuccess(
                    response.body!!.string()
                )
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                call.cancel()
                onError(e)
            }
        })
    }

    private fun makeRequest(url: String) =
        Request.Builder()
            .header(AUTH_HEADER_KEY, BEARER_FORMAT.format(token))
            .url(url)
            .build()

    private fun String.urlWithParams(params: String) =
        this + params

    companion object {
        private const val AUTH_HEADER_KEY = "Authorization"
        private const val BEARER_FORMAT = "Bearer %s"
        private const val CRYPTO_API_URL = "https://697-crypto-api.pixelplexlabs.com/api/v1/"
    }
}