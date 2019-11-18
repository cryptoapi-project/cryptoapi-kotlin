package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.wrapper.CryptoApiCoins
import io.pixelplex.cryptoapi_android_framework.wrapper.CryptoApiCoinsImpl
import io.pixelplex.cryptoapi_android_framework.wrapper.SingletonHolder

class CryptoApiFramework private constructor(callTimeout: Long, connectTimeout: Long) {

    private val cryptoApi: CryptoApi by lazy {
        CryptoApi(callTimeout, connectTimeout)
    }

    public val cryptoApiCoins: CryptoApiCoins by lazy {
        CryptoApiCoinsImpl(cryptoApi)
    }

    @Volatile private var instance: CryptoApiFramework? = null

    companion object : SingletonHolder<CryptoApiFramework, Long, Long>(::CryptoApiFramework)


//    fun testCryptoApi(onSuccess: (CoinsResponse) -> Unit, onError: (IOException) -> Unit) {
//
//        val logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//
//        val url = "https://697-crypto-api.pixelplexlabs.com/api/v1/coins"
//
//        val clientBuilder = OkHttpClient.Builder()
//        clientBuilder.addInterceptor(logging)
//        clientBuilder.callTimeout(600, TimeUnit.MILLISECONDS)
//        clientBuilder.connectTimeout(600, TimeUnit.MILLISECONDS)
//
//        val client = clientBuilder.build()
//
//        val request = Request.Builder()
//            .url(url)
//            .build()
//
//
//
//        client.newCall(request).enqueue(object: Callback {
//            override fun onResponse(call: Call, response: Response) {
//                onSuccess(
//                    CoinsResponse(fromJson(response.body!!.string()))
//                )
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//                call.cancel()
//                onError(e)
//            }
//        })
//    }
}

