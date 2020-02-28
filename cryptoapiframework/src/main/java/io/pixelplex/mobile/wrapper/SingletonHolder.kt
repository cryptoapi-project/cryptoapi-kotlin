package io.pixelplex.mobile.wrapper

import io.pixelplex.mobile.core.CryptoApi

open class SingletonHolder<out T : Any, in CAT, in CNT, in RDT, in TKN, in URL>(creator: (CAT, CNT, RDT, TKN, URL) -> T) {
    private var creator: ((CAT, CNT, RDT, TKN, URL) -> T)? = creator

    @Volatile
    private var instance: T? = null

    fun getInstance(
        callTimeout: CAT,
        connectTimeout: CNT,
        readTimeOut: RDT,
        token: TKN,
        url: URL = CryptoApi.URL.MAINNET as URL
    ): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        synchronized(this) {
            val checkInstanceAgain = instance
            return if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(callTimeout, connectTimeout, readTimeOut, token, url)
                instance = created
                creator = null
                created
            }
        }
    }
}