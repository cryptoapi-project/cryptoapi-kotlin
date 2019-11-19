package io.pixelplex.cryptoapi_android_framework.core

import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
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

//    /**
//     * Make API-call with callbacks
//     */
//    fun <T : CryptoApiReponse> call(
//        params: String,
//        onSuccess: (T) -> Unit,
//        onError: (ApiException) -> Unit
//    ) {
//        httpClient.newCall(makeRequest(CRYPTO_API_URL.urlWithParams(params)))
//            .enqueue(TypedCallback(onSuccess, onError))
//    }
//
//    /**
//     * Make suspended API-call
//     */
//    suspend fun <T : CryptoApiReponse> call(
//        params: String
//    ): T {
//        return suspendCoroutine {
//            call<T>(params, { result ->
//                it.resumeWith(Result.success(result))
//            }, { error ->
//                it.resumeWithException(error)
//            })
//        }

    fun callApi(
        path: String,
        callback: Callback,
        vararg params: Pair<String, String>
    ) {
        get(CRYPTO_API_URL.urlWithParams(path), params = *params, responseCallback = callback)
    }

    private fun String.urlWithParams(params: String) =
        this + params

    operator fun get(
        url: String?,
        vararg params: Pair<String, String>,
        responseCallback: Callback
    ) {
        val httpBuider = url!!.toHttpUrlOrNull()!!.newBuilder()
        for ((key, value) in params) {
            httpBuider.addQueryParameter(key, value)
        }
        val request: Request =
            Request.Builder().url(httpBuider.build())
                .addHeader(AUTH_HEADER_KEY, String.format(BEARER_FORMAT, token))
                .build()
        httpClient.newCall(request).enqueue(responseCallback)
    }

    companion object {
        private const val AUTH_HEADER_KEY = "Authorization"
        private const val BEARER_FORMAT = "Bearer %s"
        private const val CRYPTO_API_URL = "https://697-crypto-api.pixelplexlabs.com/api/v1/"
    }
}


