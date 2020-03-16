package io.pixelplex.mobile.cryptoapi.wrapper

/**
 * Delegates [CryptoApiFramework] instance creating
 *
 * @author Sergey Krupenich
 */
open class InstanceHolder<out T : Any, in WR>(
    private val creator: (WR) -> T
) {
    fun getInstance(
        paramWrapper: WR = CryptoApiParamWrapper() as WR
    ): T {
        synchronized(this) {
            return creator(paramWrapper)
        }
    }
}