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
class CryptoApiFramework constructor(
    callTimeout: Long,
    connectTimeout: Long,
    readTimeout: Long,
    token: String
) {
    private val cryptoApi: CryptoApi = CryptoApi(callTimeout, connectTimeout, readTimeout, token)

    val cryptoApiCoins: CryptoApiCoins = CryptoApiCoinsImpl(cryptoApi)

    val cryptoApiEth: CryptoApiEth = CryptoApiEthImpl(cryptoApi)

    //========================== EXPERIMENTAL============================

    val generatedApiEth: EthApi = EthApiImpl(cryptoApi)

    val generatedAsyncApiEth: EthAsyncApi = EthAsyncApiImpl(cryptoApi)

    //========================== EXPERIMENTAL============================

    companion object :
        SingletonHolder<CryptoApiFramework, Long, Long, Long, String>(::CryptoApiFramework)
}