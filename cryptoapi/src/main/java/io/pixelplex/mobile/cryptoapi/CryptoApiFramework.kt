package io.pixelplex.mobile.cryptoapi

import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import io.pixelplex.mobile.cryptoapi.generation.*
import io.pixelplex.mobile.cryptoapi.wrapper.SingletonHolder

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
        CryptoApi(
            callTimeout,
            connectTimeout,
            readTimeout,
            token,
            url
        )

    val coinsApi: CoinApi = CoinApiImpl(cryptoApi)

    val coinsAsyncApi: CoinAsyncApi = CoinAsyncApiImpl(cryptoApi)

    val ethereumApi: EthApi = EthApiImpl(cryptoApi)

    val ethereumAsyncApi: EthAsyncApi = EthAsyncApiImpl(cryptoApi)

    val bitcoinApi: BtcApi = BtcApiImpl(cryptoApi)

    val bitcoinAsyncApi: BtcAsyncApi = BtcAsyncApiImpl(cryptoApi)

    val bitcoinCashApi: BchApi = BchApiImpl(cryptoApi)

    val bitcoinCashAsyncApi: BchAsyncApi = BchAsyncApiImpl(cryptoApi)

    companion object :
        SingletonHolder<CryptoApiFramework, Long, Long, Long, String, CryptoApi.URL>(::CryptoApiFramework)
}