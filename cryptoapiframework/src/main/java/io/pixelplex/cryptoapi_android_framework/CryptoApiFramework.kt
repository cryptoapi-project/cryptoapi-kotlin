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
    token: String,
    url: CryptoApi.URL
) {
    private val cryptoApi: CryptoApi =
        CryptoApi(callTimeout, connectTimeout, readTimeout, token, url)

    val coinsApi: CoinApi = CoinApiImpl(cryptoApi)

    val coinsAsyncApi: CoinAsyncApi = CoinAsyncApiImpl(cryptoApi)

    val ethereumApi: EthApi = EthApiImpl(cryptoApi)

    val ethereumAsyncApi: EthAsyncApi = EthAsyncApiImpl(cryptoApi)

    val bitcoinApi: BtcApi = BtcApiImpl(cryptoApi)

    val bitcoinAsyncApi: BtcAsyncApi = BtcAsyncApiImpl(cryptoApi)

    val bitcoinCacheApi: BchApi = BchApiImpl(cryptoApi)

    val bitcoinCacheAsyncApi: BchAsyncApi = BchAsyncApiImpl(cryptoApi)

    companion object :
        SingletonHolder<CryptoApiFramework, Long, Long, Long, String, CryptoApi.URL>(::CryptoApiFramework)
}