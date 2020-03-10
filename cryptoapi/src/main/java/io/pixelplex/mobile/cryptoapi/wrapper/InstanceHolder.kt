package io.pixelplex.mobile.cryptoapi.wrapper

import io.pixelplex.mobile.cryptoapi.core.CryptoApi

open class InstanceHolder<out T : Any, in CAT, in CNT, in RDT, in TKN, in URL>(
    private val creator: (CAT, CNT, RDT, TKN, URL) -> T
) {
    fun getInstance(
        callTimeout: CAT,
        connectTimeout: CNT,
        readTimeOut: RDT,
        token: TKN,
        url: URL = CryptoApi.URL.MAINNET as URL
    ): T {
        synchronized(this) {
            return creator(callTimeout, connectTimeout, readTimeOut, token, url)
        }
    }
}