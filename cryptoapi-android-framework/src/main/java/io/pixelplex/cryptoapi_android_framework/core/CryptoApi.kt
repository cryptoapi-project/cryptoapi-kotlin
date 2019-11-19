package io.pixelplex.cryptoapi_android_framework.core

import io.pixelplex.cryptoapi_android_framework.exception.NetworkException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

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
        method: RequestMethod,
        body: String? = null,
        onSuccess: (String) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        httpClient.newCall(
            makeRequest(
                CRYPTO_API_URL.urlWithParams(params),
                method,
                body
            )
        ).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                onSuccess(
                    response.body!!.string()
                )
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                call.cancel()
                onError(e as NetworkException)
            }
        })
    }

    private fun makeRequest(
        url: String,
        method: RequestMethod,
        body: String?
    ): Request {
        val requestBuilder = Request.Builder()
            .header(AUTH_HEADER_KEY, BEARER_FORMAT.format(token))
            .url(url)

        if (method == RequestMethod.POST) {
            requestBuilder.post(
                getRequestBody(body)
            )
        }

        return requestBuilder.build()
    }

    private fun String.urlWithParams(params: String) =
        this + params

    private fun getRequestBody(body: String?) =
        body?.let {
            it.toRequestBody(MEDIA_TYPE.toMediaTypeOrNull())
        } ?: EMPTY_BODY.toRequestBody(MEDIA_TYPE.toMediaTypeOrNull())

    companion object {
        private const val AUTH_HEADER_KEY = "Authorization"
        private const val BEARER_FORMAT = "Bearer %s"
        private const val CRYPTO_API_URL = "https://697-crypto-api.pixelplexlabs.com/api/v1/"
        private const val MEDIA_TYPE = "application/json; charset=utf-8"
        private const val EMPTY_BODY = ""
    }

    enum class RequestMethod {
        POST,
        GET
    }
}