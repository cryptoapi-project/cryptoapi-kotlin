package io.pixelplex.cryptoapi_android_framework.core

import io.pixelplex.cryptoapi_android_framework.core.model.response.CryptoApiReponse
import io.pixelplex.cryptoapi_android_framework.exception.ApiException
import io.pixelplex.cryptoapi_android_framework.support.fromJson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.lang.annotation.ElementType
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    /**
     * Make API-call with callbacks
     */
    fun <T : CryptoApiReponse> call(
        params: String,
        onSuccess: (T) -> Unit,
        onError: (ApiException) -> Unit
    ) {
        httpClient.newCall(makeRequest(CRYPTO_API_URL.urlWithParams(params)))
            .enqueue(TypedCallback(onSuccess, onError))
    }

    /**
     * Make suspended API-call
     */
    suspend fun <T : CryptoApiReponse> call(
        params: String
    ): T {
        return suspendCoroutine {
            call<T>(params, { result ->
                it.resumeWith(Result.success(result))
            }, { error ->
                it.resumeWithException(error)
            })
        }
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

private class TypedCallback<T>(
    private val onSuccess: (T) -> Unit,
    private val onError: (ApiException) -> Unit
) : Callback {

    override fun onFailure(call: Call, e: IOException) {
        onError(ApiException(e))
    }

    override fun onResponse(call: Call, response: Response) {
        onSuccess(fromJson(response.body?.string()))
    }

}

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class GET

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class POST


