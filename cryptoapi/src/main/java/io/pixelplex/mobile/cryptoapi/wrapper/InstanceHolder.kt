package io.pixelplex.mobile.cryptoapi.wrapper

import io.pixelplex.mobile.cryptoapi.core.CryptoApi

/**
 * Delegates [CryptoApiFramework] instance creating
 *
 * @author Sergey Krupenich
 */
open class InstanceHolder<out T : Any, in TKN, in URL,  in CAT, in CNT, in RDT>(
    private val creator: (TKN, URL, CAT, CNT, RDT) -> T
) {
    fun getInstance(
        token: TKN,
        url: URL = CryptoApi.URL.MAINNET as URL,
        callTimeout: CAT = DefaultInstanceParams.CALL_TIMEOUT as CAT,
        connectTimeout: CNT = DefaultInstanceParams.CONNECT_TIMEOUT as CNT,
        readTimeOut: RDT = DefaultInstanceParams.READ_TIMEOUT as RDT
    ): T {
        synchronized(this) {
            return creator(token, url, callTimeout, connectTimeout, readTimeOut)
        }
    }
}

object DefaultInstanceParams {
    const val CALL_TIMEOUT = 30000L
    const val READ_TIMEOUT = 30000L
    const val CONNECT_TIMEOUT = 15000L
}