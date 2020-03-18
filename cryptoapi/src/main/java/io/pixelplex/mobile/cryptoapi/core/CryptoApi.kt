package io.pixelplex.mobile.cryptoapi.core

import io.pixelplex.mobile.cryptoapi.BuildConfig
import io.pixelplex.mobile.cryptoapi.model.generation.QueryParameter
import io.pixelplex.mobile.cryptoapi.model.generation.QueryType
import io.pixelplex.mobile.cryptoapi.model.generation.RequestParameter
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
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
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class CryptoApi(
    private val cryptoApiParams: CryptoApiConfiguration
) {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(logging)
        callTimeout(cryptoApiParams.callTimeout, TimeUnit.MILLISECONDS)
        connectTimeout(cryptoApiParams.connectTimeout, TimeUnit.MILLISECONDS)
        readTimeout(cryptoApiParams.readTimeOut, TimeUnit.MILLISECONDS)
    }.build()

    fun callApi(
        params: String,
        method: RequestMethod = RequestMethod.GET,
        body: String? = null,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        httpClient.newCall(
            makeRequest(
                cryptoApiParams.url.path.urlWithPath(params),
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
        val requestBuilder = Request.Builder().url(url)
        buildRequest(method, requestBuilder, body)

        return requestBuilder.build()
    }

    private fun buildRequest(
        method: RequestMethod,
        requestBuilder: Request.Builder,
        body: String?
    ) {
        when (method) {
            RequestMethod.POST -> {
                requestBuilder.post(
                    getRequestBody(body)
                )
            }
            RequestMethod.DELETE -> {
                requestBuilder.delete(
                    getRequestBody(body)
                )
            }
            else -> {
            }
        }
    }

    private fun String.urlWithPath(path: String): String =
        this + path

    private fun getRequestBody(body: String?) =
        body?.let {
            it.toRequestBody(MEDIA_TYPE.toMediaTypeOrNull())
        } ?: EMPTY_BODY.toRequestBody(
            MEDIA_TYPE.toMediaTypeOrNull()
        )

    companion object {
        private const val MEDIA_TYPE = "application/json; charset=utf-8"
        private const val EMPTY_BODY = ""

        private const val TOKEN = "token"

        private val PATH_REGEXP_PATTERN = "\\{(.*?)\\}"
    }

    enum class URL(val path: String) {
        STAGE("https://697-crypto-api-api.pixelplexlabs.com/api/v1/"),
        DEV("https://697-crypto-api-api.pixelplex-test.by/api/v1/"),
        MAINNET("https://api.apikey.io/api/v1/"),
        TESTNET("https://testnet-api.apikey.io/api/v1/")
    }

    private val regexPatternUrl = Pattern.compile(
        PATH_REGEXP_PATTERN
    )

    fun callApi(
        path: String,
        callback: Callback,
        params: List<QueryParameter<*>> = emptyList(),
        callMethod: String
    ) {
        get(
            url = cryptoApiParams.url.path.urlWithPath(path),
            params = params,
            responseCallback = callback,
            callMethod = callMethod
        )
    }

    operator fun get(
        url: String,
        params: List<QueryParameter<*>> = emptyList(),
        responseCallback: Callback,
        callMethod: String
    ) {
        var requestUrl = url
        val paths = params.filter { it.type == QueryType.PATH }

        if (paths.isNotEmpty()) {
            val matcher = regexPatternUrl.matcher(requestUrl)
            while (matcher.find()) {
                val param = matcher.group(0)
                requestUrl = requestUrl.replace(
                    param,
                    paths.find { "{${it.name}}" == param }!!.value.toQueryParameter()
                )
            }
        }

        val httpBuilder = requestUrl.toHttpUrlOrNull()!!.newBuilder()

        params.filter { it.type == QueryType.QUERY }.forEach { param ->
            httpBuilder.addQueryParameter(param.name, param.value.toQueryParameter())
        }

        httpBuilder.addQueryParameter(TOKEN, BuildConfig.CRYPTO_API_KEY)

        val requestBuilder =
            Request.Builder().url(httpBuilder.build())

        handleRequestByMethod(callMethod, params, requestBuilder)

        val request = requestBuilder.build()
        httpClient.newCall(request).enqueue(responseCallback)

        if (BuildConfig.DEBUG) {
            println("================ REQUEST ${requestUrl} =====================")
            println("PARAMETERS:")
            params.forEach {
                println("[${it.type.name}] ${it.name}: ${it.value}")
            }
            println("FULL REQUEST:")
            println(request.toString())
            println("================ END REQUEST =========================")
        }
    }

    private fun handleRequestByMethod(
        callMethod: String,
        params: List<QueryParameter<*>>,
        requestBuilder: Request.Builder
    ) {
        when (RequestMethod.valueOf(callMethod.toUpperCase(Locale.getDefault()))) {
            RequestMethod.POST -> {
                requestPost(params, requestBuilder)
            }
            RequestMethod.DELETE -> {
                requestDelete(params, requestBuilder)
            }
            else -> {
            }
        }
    }

    private fun requestPost(
        params: List<QueryParameter<*>>,
        requestBuilder: Request.Builder
    ) {
        params.filter { it.type == QueryType.BODY }.forEach { param ->
            requestBuilder.post(getRequestBody(param.value.toGson()))
        }
    }

    private fun requestDelete(
        params: List<QueryParameter<*>>,
        requestBuilder: Request.Builder
    ) {
        val bodyParams = params.filter { it.type == QueryType.BODY }
        if (bodyParams.count() > 0) {
            bodyParams.forEach { param ->
                requestBuilder.delete(getRequestBody(param.value.toGson()))
            }
        } else {
            requestBuilder.delete()
        }
    }

    private fun RequestParameter<*>.toQueryParameter(): String =
        if (this.value is List<*>) {
            (this.value!! as List<*>).joinToString(",")
        } else {
            this.value.toString()
        }

    enum class RequestMethod {
        POST,
        GET,
        DELETE
    }
}