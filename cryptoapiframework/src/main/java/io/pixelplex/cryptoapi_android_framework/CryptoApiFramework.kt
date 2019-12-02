package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.generation.*
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

    val coinsApi: CoinApi = CoinApiImpl(cryptoApi)

    val coinsAsyncApi: CoinAsyncApi = CoinAsyncApiImpl(cryptoApi)

    val generatedApiEth: EthApi = EthApiImpl(cryptoApi)

    val generatedAsyncApiEth: EthAsyncApi = EthAsyncApiImpl(cryptoApi)

    companion object :
        SingletonHolder<CryptoApiFramework, Long, Long, Long, String>(::CryptoApiFramework)
}