package io.pixelplex.cryptoapi_android_framework.core

import io.pixelplex.cryptoapi_android_framework.BuildConfig
import io.pixelplex.model.QueryParameter
import io.pixelplex.model.QueryType
import io.pixelplex.model.RequestParameter
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class CryptoApi(
    private val callTimeout: Long,
    private val connectTimeout: Long,
    private val readTimeout: Long,
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
            readTimeout(readTimeout, TimeUnit.MILLISECONDS)
        }

        builder.build()
    }

    fun callApi(
        params: String,
        method: RequestMethod = RequestMethod.GET,
        body: String? = null,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        httpClient.newCall(
            makeRequest(
                CRYPTO_API_URL.urlWithPath(params),
                method,
                body
            )
        ).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    onSuccess(response.body!!.string())
                } else {
                    onError(response.body!!.string())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                call.cancel()
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
        params: List<QueryParameter<*>> = emptyList()
    ) {
        get(CRYPTO_API_URL.urlWithPath(path), params = params, responseCallback = callback)
    }

    operator fun get(
        url: String,
        params: List<QueryParameter<*>> = emptyList(),
        responseCallback: Callback
    ) {

        var requestUrl = url

        val paths = params.filter { it.type == QueryType.PATH }

        if (paths.isNotEmpty()) {
            val matcher = PATH_REGEXP_PATTERN.matcher(requestUrl)
            while (matcher.find()) {
                val param = matcher.group(0)
                requestUrl = requestUrl.replace(
                    "{$param}",
                    paths.find { it.name == param }!!.value.toQueryParameter()
                )
            }
        }

        val httpBuilder = requestUrl.toHttpUrlOrNull()!!.newBuilder()

        params.filter { it.type == QueryType.QUERY }.forEach { param ->
            httpBuilder.addQueryParameter(param.name, param.value.toQueryParameter())
        }

        val requestBuilder =
            Request.Builder().url(httpBuilder.build())
                .addHeader(AUTH_HEADER_KEY, String.format(BEARER_FORMAT, token))

        params.filter { it.type == QueryType.BODY }.forEach { param ->
            requestBuilder.post(getRequestBody(param.value.toGson()))
        }

        val request = requestBuilder.build()
        httpClient.newCall(request).enqueue(responseCallback)

        if (BuildConfig.DEBUG) {
            println("================REQUEST ${requestUrl}=====================")
            println("PARAMETERS:")
            params.forEach {
                println("[${it.type.name}] ${it.name}: ${it.value}")
            }
            println("FULL REQUEST:")
            println(request.toString())
            println("================END REQUEST=========================")
        }
    }

    private fun RequestParameter<*>.toQueryParameter(): String {
        return if (this.value is List<*>) {
            (this.value!! as List<*>).joinToString(",")
        } else {
            this.value.toString()
        }
    }


    //========================== EXPERIMENTAL============================ НЕ ЧАПАЦЬ

    enum class RequestMethod {
        POST,
        GET
    }
}

