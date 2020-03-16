package io.pixelplex.mobile.cryptoapi.wrapper

import io.pixelplex.mobile.cryptoapi.core.CryptoApi

/**
 * Holds all parameters for [CryptoApiFramework] instance init.
 *
 * @author Sergey Krupenich
 */
data class CryptoApiParamWrapper(
    val url: CryptoApi.URL = CryptoApi.URL.MAINNET,
    val callTimeout: Long = CALL_TIMEOUT,
    val connectTimeout: Long = CONNECT_TIMEOUT,
    val readTimeOut: Long = READ_TIMEOUT
) {
    companion object {
        const val CALL_TIMEOUT = 30000L
        const val READ_TIMEOUT = 30000L
        const val CONNECT_TIMEOUT = 15000L
    }
}