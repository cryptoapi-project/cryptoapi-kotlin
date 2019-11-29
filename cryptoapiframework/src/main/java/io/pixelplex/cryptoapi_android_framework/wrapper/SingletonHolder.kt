package io.pixelplex.cryptoapi_android_framework.wrapper

open class SingletonHolder<out T : Any, in CAT, in CNT, in RDT, in TKN>(creator: (CAT, CNT, RDT, TKN) -> T) {
    private var creator: ((CAT, CNT, RDT, TKN) -> T)? = creator

    @Volatile
    private var instance: T? = null

    fun getInstance(callTimeout: CAT, connectTimeout: CNT, readTimeOut: RDT, token: TKN): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        synchronized(this) {
            val checkInstanceAgain = instance
            return if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(callTimeout, connectTimeout, readTimeOut, token)
                instance = created
                creator = null
                created
            }
        }
    }
}