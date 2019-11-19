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

    companion object : SingletonHolder<CryptoApiFramework, Long, Long>(::CryptoApiFramework)
}