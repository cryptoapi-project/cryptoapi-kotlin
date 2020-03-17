package io.pixelplex.mobile.cryptoapi

import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import io.pixelplex.mobile.cryptoapi.generation.*
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
import io.pixelplex.mobile.cryptoapi.wrapper.InstanceHolder

/**
 * Delegates all logic to specific wrapper services which associated
 * Crypto API calls
 *
 * @author Sergey Krupenich
 */
class CryptoApiFramework constructor(
    cryptoApiParams: CryptoApiConfiguration
) {
    private val cryptoApi: CryptoApi =
        CryptoApi(
            cryptoApiParams
        )

    val coinsApi: CoinApi = CoinApiImpl(cryptoApi)

    val coinsAsyncApi: CoinAsyncApi = CoinAsyncApiImpl(cryptoApi)

    val ratesApi: RatesApi = RatesApiImpl(cryptoApi)

    val ratesAsyncApi: RatesAsyncApi = RatesAsyncApiImpl(cryptoApi)

    val ethereumApi: EthApi = EthApiImpl(cryptoApi)

    val ethereumAsyncApi: EthAsyncApi = EthAsyncApiImpl(cryptoApi)

    val bitcoinApi: BtcApi = BtcApiImpl(cryptoApi)

    val bitcoinAsyncApi: BtcAsyncApi = BtcAsyncApiImpl(cryptoApi)

    val bitcoinCashApi: BchApi = BchApiImpl(cryptoApi)

    val bitcoinCashAsyncApi: BchAsyncApi = BchAsyncApiImpl(cryptoApi)

    companion object :
        InstanceHolder<CryptoApiFramework, CryptoApiConfiguration>(::CryptoApiFramework)
}