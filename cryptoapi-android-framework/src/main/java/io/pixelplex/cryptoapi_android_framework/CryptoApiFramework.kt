package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.generation.EthApi
import io.pixelplex.cryptoapi_android_framework.generation.EthApiImpl
import io.pixelplex.cryptoapi_android_framework.generation.EthAsyncApi
import io.pixelplex.cryptoapi_android_framework.generation.EthAsyncApiImpl
import io.pixelplex.cryptoapi_android_framework.wrapper.CryptoApiCoins
import io.pixelplex.cryptoapi_android_framework.wrapper.CryptoApiCoinsImpl
import io.pixelplex.cryptoapi_android_framework.wrapper.CryptoApiEth
import io.pixelplex.cryptoapi_android_framework.wrapper.CryptoApiEthImpl
import io.pixelplex.cryptoapi_android_framework.wrapper.SingletonHolder

/**
 * Delegates all logic to specific wrapper services which associated
 * Crypto API calls
 *
 * @author Sergey Krupenich
 */
class CryptoApiFramework private constructor(
    callTimeout: Long,
    connectTimeout: Long,
    token: String
) {
    private val cryptoApi: CryptoApi by lazy {
        CryptoApi(callTimeout, connectTimeout, token)
    }

    public val cryptoApiCoins: CryptoApiCoins by lazy {
        CryptoApiCoinsImpl(cryptoApi)
    }

    public val cryptoApiEth: CryptoApiEth by lazy {
        CryptoApiEthImpl(cryptoApi)
    }

    //========================== EXPERIMENTAL============================ НЕ ЧАПАЦЬ
    val generatedApiEth: EthApi by lazy {
        EthApiImpl(cryptoApi)
    }
    val generatedAsyncApiEth: EthAsyncApi by lazy {
        EthAsyncApiImpl(cryptoApi)
    }
    //========================== EXPERIMENTAL============================ НЕ ЧАПАЦЬ

    companion object : SingletonHolder<CryptoApiFramework, Long, Long, String>(::CryptoApiFramework)
}