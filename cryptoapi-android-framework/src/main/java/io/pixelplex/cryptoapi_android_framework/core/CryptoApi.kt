package io.pixelplex.cryptoapi_android_framework.core


import io.pixelplex.model.QueryParameter
import io.pixelplex.model.QueryType
import io.pixelplex.model.exception.NetworkException
import okhttp3.*

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.regex.Pattern

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
        params: String,
        method: RequestMethod = RequestMethod.GET,
        body: String? = null,
        onSuccess: (String) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        httpClient.newCall(
            makeRequest(
                CRYPTO_API_URL.urlWithPath(params),
                method,
                body
            )
        ).enqueue(object : Callback {
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

    private fun String.urlWithPath(path: String): String =
        this + path

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

        private val PATH_REGEXP_PATTERN = Pattern.compile("[^{\\}]+(?=})")
    }

    //========================== EXPERIMENTAL============================ НЕ ЧАПАЦЬ

    fun callApi(
        path: String,
        callback: Callback,
        params: List<QueryParameter>
    ) {
        get(CRYPTO_API_URL.urlWithPath(path), params = params, responseCallback = callback)
    }

    operator fun get(
        url: String,
        params: List<QueryParameter>,
        responseCallback: Callback
    ) {

        var path = url

        val httpBuilder = path.toHttpUrlOrNull()!!.newBuilder()

        params.filter { it.type == QueryType.QUERY }.forEach { param ->
            httpBuilder.addQueryParameter(param.name, param.value.toString())
        }

        val paths = params.filter { it.type == QueryType.PATH }

        if (paths.isNotEmpty()) {
            val matcher = PATH_REGEXP_PATTERN.matcher(path)
            while (matcher.find()) {
                val param = matcher.group(0)
                path = path.replace("{$param}", paths.find { it.name == param }!!.value.toString())
            }
        }

        val request: Request =
            Request.Builder().url(httpBuilder.build())
                .addHeader(AUTH_HEADER_KEY, String.format(BEARER_FORMAT, token))
                .url(path)
                .build()

        httpClient.newCall(request).enqueue(responseCallback)
    }

    //========================== EXPERIMENTAL============================ НЕ ЧАПАЦЬ

    enum class RequestMethod {
        POST,
        GET
    }
}

