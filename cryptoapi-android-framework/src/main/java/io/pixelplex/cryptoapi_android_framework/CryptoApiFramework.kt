package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.cryptoapi_android_framework.model.response.CoinsResponse
import io.pixelplex.cryptoapi_android_framework.support.fromJson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class CryptoApiFramework {
    fun testCryptoApi(onSuccess: (CoinsResponse) -> Unit, onError: (IOException) -> Unit) {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val url = "https://697-crypto-api.pixelplexlabs.com/api/v1/coins"
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(logging)
        clientBuilder.callTimeout(600, TimeUnit.MILLISECONDS)
        clientBuilder.connectTimeout(600, TimeUnit.MILLISECONDS)

        val client = clientBuilder.build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                onSuccess(
                    CoinsResponse(fromJson(response.body!!.string()))
                )
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                call.cancel()
                onError(e)
            }
        })
    }
}